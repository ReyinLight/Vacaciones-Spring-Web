package com.Vacaciones.Vacaciones;

import com.Vacaciones.Vacaciones.Modelo.Empleado;
import com.Vacaciones.Vacaciones.service.XlsxService;

import java.text.DecimalFormat;

public class LocalMain {





    public static void main(String[] args) {

//
//        XlsxService x = new XlsxService();
//
//        try {
//            x.createExcel2();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//        Empleado e = new Empleado("", LocalDate.of(2022,3,7),0);
//        System.out.println(e.toString());
//        XlsxService servicio = new XlsxService();
////        servicio.eliminarArchivos();
//
//        servicio.modificarExcelLocal();

        double a = 1.1234567896;

        System.out.println(new DecimalFormat("#.##").format(a));







    }

}
