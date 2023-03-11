package com.Vacaciones.Vacaciones.service;

import com.Vacaciones.Vacaciones.Modelo.Empleado;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Service
public class XlsxService {


    private final File DIRECTORIO = new File("Vacaciones/cargas/");
    private File file;


    public ByteArrayInputStream modificarExcelWeb(){


        SXSSFWorkbook workbook = new SXSSFWorkbook(1);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Empleado empleado;
        try {
            file = leerArchivo();
            FileInputStream fileStream = new FileInputStream(file);


            Sheet hoja = workbook.getSheet("Vacaciones");
            Row row;
            Cell cel;


            double vacacionesTotales;
            double vacacionesRestantes;

            for (int i = 3; i <= hoja.getLastRowNum(); i++) {
                empleado = new Empleado(
                        hoja.getRow(i).getCell(((int)'A'-65)).getStringCellValue(),
                        LocalDate.of(
                                (int) hoja.getRow(i).getCell(((int)'D'-65)).getNumericCellValue(),
                                (int) hoja.getRow(i).getCell(((int)'C'-65)).getNumericCellValue(),
                                (int) hoja.getRow(i).getCell(((int)'B'-65)).getNumericCellValue()
                        ),
                        (int) hoja.getRow(i).getCell((int)'M'-65).getNumericCellValue()
                );

                vacacionesTotales = empleado.calcularDesdeNuevaLey();
                vacacionesRestantes = vacacionesTotales - empleado.getVacacionesTomadas();

                hoja.getRow(i).getCell((int)'R'-65).setCellValue(vacacionesTotales);
                hoja.getRow(i).getCell((int)'S'-65).setCellValue(vacacionesTotales);
            }

            workbook.write(stream);
            workbook.dispose();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return new ByteArrayInputStream(stream.toByteArray());
    }



    public void modificarExcelLocal() {

        List<Empleado> listaEmpleados = null;
        Empleado empleado;

        try {
            file = leerArchivo();
            FileInputStream fileStream = new FileInputStream(file);

            XSSFWorkbook workbook = new XSSFWorkbook(fileStream);


            Sheet hoja = workbook.getSheet("Vacaciones");
            Row row;
            Cell cel;


            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            double vacacionesTotales;
            double vacacionesRestantes;

            for (int i = 3; i <= hoja.getLastRowNum(); i++) {
                empleado = new Empleado(
                        hoja.getRow(i).getCell(((int)'A'-65)).getStringCellValue(),
                        LocalDate.of(
                                (int) hoja.getRow(i).getCell(((int)'D'-65)).getNumericCellValue(),
                                (int) hoja.getRow(i).getCell(((int)'C'-65)).getNumericCellValue(),
                                (int) hoja.getRow(i).getCell(((int)'B'-65)).getNumericCellValue()
                        ),
                        (int) hoja.getRow(i).getCell((int)'M'-65).getNumericCellValue()
                );


                hoja.getRow(i).getCell((int)'S'-65).setCellValue(empleado.calcularDesdeNuevaLey() - empleado.getVacacionesTomadas());
            }

            createExcelLocal(workbook);





        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }

    public File leerArchivo(){
        return DIRECTORIO.listFiles()[0];
    }



    // Lee e imprime un excel en consola
    public void leerExcelConsola(){
        try {
            FileInputStream fileStream = new FileInputStream("Vacaciones/cargas/ecsel.xlsx");

            XSSFWorkbook workbook = new XSSFWorkbook(fileStream);

            Sheet hoja = workbook.getSheetAt(0);
            Iterator<Row> rows = hoja.iterator();
            Iterator<Cell> cells;

            Row row;
            Cell cel;
            while(rows.hasNext()){
                row = rows.next();

                cells = row.iterator();
                while(cells.hasNext()){
                    cel = cells.next();

                    System.out.println(
                            cel.getCellType() == CellType.NUMERIC
                                    ? cel.getNumericCellValue()
                                    : cel.getStringCellValue());
                }
                System.out.println("");
                System.out.println("");
                System.out.println("");
            }


            workbook.close();


        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    // Metodo que elimina todos los archivos dentro de Vacaciones/cargas/
    public void eliminarArchivos(){

        file = null;

        File[] files = DIRECTORIO.listFiles();

        for(int i = 0 ; i < files.length; i++){
            file = files[i];
            file.delete();
        }

        System.out.println("Se eliminó todo");

    }

    // Creacion de excel usando  SCSSFWorkbook
    public ByteArrayInputStream createExcel(List<Empleado> empleados) throws Exception {
        String[] columns ={"Nombre", "Fecha", "Vacaciones totales", "Vacaciones tomadas", "Restantes"};

        //Workbook workbook = new HSSFWorkbook(); // Esta no sirvió
        SXSSFWorkbook workbook = new SXSSFWorkbook(1);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("Personas");

        Row row = sheet.createRow(0);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columns[i]);
        }

        int initRow = 1;
        for (Empleado empleado: empleados) {
            row = sheet.createRow(initRow);
            row.createCell(0).setCellValue(empleado.getNombre());
            row.createCell(1).setCellValue(empleado.getFechaIngreso());
            row.createCell(2).setCellValue(empleado.calcularDesdeNuevaLey());
            row.createCell(3).setCellValue(empleado.getVacacionesTomadas());
            row.createCell(4).setCellValue(empleado.calcularDesdeNuevaLey() - empleado.getVacacionesTomadas());

            initRow++;
        }

        workbook.write(stream);
        //workbook.close();
        workbook.dispose();


        return new ByteArrayInputStream(stream.toByteArray());

    }

//    public ByteArrayOutputStream createExcelWeb(SXSSFWorkbook workbook){
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        workbook.write(stream);
//
//        return new ByteArrayOutputStream(stream.toByteArray());
//    }
    public void createExcelLocal(XSSFWorkbook workbook){

        try{
            FileOutputStream fileOutput = new FileOutputStream(file.getAbsolutePath()+2);

            workbook.write(fileOutput);
            workbook.close();
            fileOutput.close();
        }catch(Exception e){
            System.out.println("ERRORRRRR: " + e.getMessage());
        }
    }




}
