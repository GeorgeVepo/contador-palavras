package com.treinamento.contadorpalavras.service;

import com.treinamento.contadorpalavras.Repositorio.TipoDocumentoRepositorio;
import com.treinamento.contadorpalavras.Repositorio.TipoUsuarioRepositorio;
import com.treinamento.contadorpalavras.Repositorio.UsuarioRepositorio;
import com.treinamento.contadorpalavras.dto.ContagemPalavrasDto;
import com.treinamento.contadorpalavras.dto.ProcessarDocumentoDto;
import com.treinamento.contadorpalavras.exception.ForbiddenException;
import com.treinamento.contadorpalavras.model.TipoDocumento;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import com.treinamento.contadorpalavras.exception.BadRequestException;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DocumentoService {
    private final UsuarioRepositorio usuarioRepositorio;
    private final TipoUsuarioRepositorio tipoUsuarioRepositorio;
    private final TipoDocumentoRepositorio tipoDocumentoRepositorio;

    public String processarDocumento(ProcessarDocumentoDto processarDocumentoDto) {
        verificaUsuarioExistente(processarDocumentoDto.getIdUsuario());

        verificarTipoDocumentoPermitido(processarDocumentoDto.getIdUsuario(),
                processarDocumentoDto.getIdTipoDocumento());

        verificarTipoAplicacaoPermitido(processarDocumentoDto.getIdUsuario(),
                processarDocumentoDto.getIdTipoAplicacao());

        return converterEmExcel(leituraArquivo(processarDocumentoDto));
    }

    public String converterEmExcel(ArrayList<ContagemPalavrasDto> contagemPalavrasList){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ContagemPalavras");

        definirTamanhoColunas(sheet);

        criarCabecalho(workbook, sheet);

        return escreverNaPlanilha(contagemPalavrasList, sheet, workbook);
    }


    public String escreverNaPlanilha(ArrayList<ContagemPalavrasDto> contagemPalavrasList,
                                   Sheet sheet, Workbook workbook){

        CellStyle style = definirEstiloDemaisLinhas(workbook);

        int rowIndex = 1;

        Row row;

        for (ContagemPalavrasDto contagemPalvra: contagemPalavrasList) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
            Cell cell = row.createCell(0);
            cell.setCellValue(contagemPalvra.getPalavra());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(contagemPalvra.getContagem());
            cell.setCellStyle(style);
        }

        ByteArrayOutputStream b = new ByteArrayOutputStream();

        try {
            workbook.write(b);
        }catch(Exception e){
            throw new RuntimeException("Erro na transformação do documento para .xls");
        }
        byte[] bytes = b.toByteArray(
        );

        return new String(Base64.getEncoder().encode(bytes));
    }

    public CellStyle definirEstiloDemaisLinhas(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        return style;
    }

    public CellStyle definirEstiloCabecalho(Workbook workbook){
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        return headerStyle;
    }

    public void definirTamanhoColunas(Sheet sheet){
        sheet.setColumnWidth(0, 10000);
        sheet.setColumnWidth(1, 4000);
    }

    public void criarCabecalho(Workbook workbook, Sheet sheet){
        Row header = sheet.createRow(0);

        CellStyle headerStyle = definirEstiloCabecalho(workbook);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Palavra");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Contagem");
        headerCell.setCellStyle(headerStyle);

    }


    public ArrayList<ContagemPalavrasDto> leituraArquivo(ProcessarDocumentoDto processarDocumentoDto) {

          try {

            TipoDocumento tipoDocumento = obterTipoDocumento(processarDocumentoDto.getIdTipoDocumento());

            switch (tipoDocumento.getNome()) {
                case "img":
                    return mockImagem();
                case "pdf":
                    return contagemPalavrasPdf(processarDocumentoDto);
                case "txt":
                    return contagemPalavrasTxt(lerArquivoTxt(processarDocumentoDto.getDocumentoBase64()));

            }
            } catch (IOException e) {
                e.printStackTrace();
            }
          return new ArrayList<>();
    }

    public ArrayList<ContagemPalavrasDto> contagemPalavrasPdf(ProcessarDocumentoDto processarDocumentoDto) throws IOException {
        ArrayList<ContagemPalavrasDto> contagemPalavrasList = new ArrayList<>();
        return contagemPalavras(leituraArquivoPDF(processarDocumentoDto), contagemPalavrasList);
    }

    public Matcher leituraArquivoPDF(ProcessarDocumentoDto processarDocumentoDto) throws IOException {
        byte[] doc = Base64.getDecoder().decode(processarDocumentoDto.getDocumentoBase64());
        PDDocument document = PDDocument.load(doc);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        return separaTextoEmPalavras(text);
    }


    public ArrayList<ContagemPalavrasDto> contagemPalavrasTxt(BufferedReader bfReader) throws IOException {
        ArrayList<ContagemPalavrasDto> contagemPalavrasList = new ArrayList<>();
        String linha = bfReader.readLine();

        Matcher matcher;

        while (linha != null) {

            matcher = separaTextoEmPalavras(linha);

            contagemPalavrasList = contagemPalavras(matcher, contagemPalavrasList);

            linha = bfReader.readLine();
        }

        bfReader.close();

        return contagemPalavrasList;
    }


    public ArrayList<ContagemPalavrasDto> contagemPalavras(Matcher matcher, ArrayList<ContagemPalavrasDto> contagemPalavrasList){
        ContagemPalavrasDto contagemPalavrasDto;

        while (matcher.find()) {
            String palavra = matcher.group(1);

            Stream<ContagemPalavrasDto> streamContagem = contagemPalavrasList.stream()
                    .filter(c -> c.getPalavra().equalsIgnoreCase(palavra));

            Optional<ContagemPalavrasDto> contagemPalavrasOp = streamContagem.findFirst();
            contagemPalavrasDto = new ContagemPalavrasDto();

            if (!contagemPalavrasOp.isPresent()) {
                contagemPalavrasDto.setPalavra(palavra);
                contagemPalavrasDto.setContagem(1);
                contagemPalavrasList.add(contagemPalavrasDto);
            } else {
                contagemPalavrasDto = contagemPalavrasOp.get();
                contagemPalavrasDto.setContagem(1 + contagemPalavrasDto.getContagem());

                contagemPalavrasList.set(contagemPalavrasList
                        .indexOf(contagemPalavrasDto), contagemPalavrasDto);
            }
        }
        return contagemPalavrasList;
    }


    public ArrayList<ContagemPalavrasDto> mockImagem() {
        ArrayList<ContagemPalavrasDto> contagemPalavrasList = new ArrayList<>();

        ContagemPalavrasDto contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("mock");
        contagemPalavrasDto.setContagem(10);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("teste");
        contagemPalavrasDto.setContagem(9);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("maquina");
        contagemPalavrasDto.setContagem(8);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("radar");
        contagemPalavrasDto.setContagem(7);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("problema");
        contagemPalavrasDto.setContagem(6);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("antena");
        contagemPalavrasDto.setContagem(5);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("inteligência");
        contagemPalavrasDto.setContagem(4);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("sombra");
        contagemPalavrasDto.setContagem(3);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("sonic");
        contagemPalavrasDto.setContagem(2);
        contagemPalavrasList.add(contagemPalavrasDto);

        contagemPalavrasDto = new ContagemPalavrasDto();
        contagemPalavrasDto.setPalavra("blade");
        contagemPalavrasDto.setContagem(1);
        contagemPalavrasList.add(contagemPalavrasDto);

        return contagemPalavrasList;
    }

    public TipoDocumento obterTipoDocumento(Long idTipoDocumento) {
        return tipoDocumentoRepositorio.findById(idTipoDocumento)
                .orElseThrow(() -> new BadRequestException("Tipo de documento informado é inválido!"));
    }

    public Matcher separaTextoEmPalavras(String linha) {
        String minusculo = linha.toLowerCase();

        Pattern p = Pattern.compile("(\\w{3,}+)(?:[\\s\\.\\,])");
        return p.matcher(minusculo);
    }

    public BufferedReader lerArquivoTxt(String base64) {
        byte[] content = Base64.getDecoder().decode(base64);

        InputStream is = new ByteArrayInputStream(content);
        return new BufferedReader(new InputStreamReader(is));


    }


    public void verificaUsuarioExistente(Long idUsuario) {
        usuarioRepositorio.findById(idUsuario).orElseThrow(
                () -> new BadRequestException("Usuário não existe!"));
    }

    public void verificarTipoDocumentoPermitido(Long idUsuario, Long idTipoDocumento) {
        int count = tipoUsuarioRepositorio.possuiPermissaoTipoDocumento(idUsuario, idTipoDocumento);
        if (count == 0) {
            throw new ForbiddenException("Usuário não possui permissão para o tipo de documento!");
        }
    }

    public void verificarTipoAplicacaoPermitido(Long idUsuario, Long idTipoAplicacao) {
        int count = tipoUsuarioRepositorio.possuiPermissaoTipoAplicacao(idUsuario, idTipoAplicacao);
        if (count == 0) {
            throw new ForbiddenException("Usuário não possui permissão para o tipo de aplicação!");
        }
    }


}
