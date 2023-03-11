package com.Vacaciones.Vacaciones.Modelo;

import com.Vacaciones.Vacaciones.service.XlsxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmpleadoDAO {

    @Autowired
    XlsxService xlsxService;

    public List<Empleado> cargarExcel(String ruta){
        List<Empleado> lista = new ArrayList<>();
        return lista;
    }

    public List<Empleado> getEmpleadosHARDCODE(){
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado("Acevedo Ceron Luis Rodrigo", LocalDate.of(2022,7,1), 2));
        empleados.add(new Empleado("Aguilera Corona Fausto", LocalDate.of(2022,5,16), 0));
        empleados.add(new Empleado("Alcaraz Solis Barbara", LocalDate.of(2013,2,7), 80));

        empleados.add(new Empleado("Alvarado Mendoza Angelica Alejandra", LocalDate.of(2022,5,23), 0));
        empleados.add(new Empleado("Angel Cornejo Sergio", LocalDate.of(2014,3,18), 65));
        empleados.add(new Empleado("Arroyo Aldana Araceli", LocalDate.of(2010,7,18), 141));
        empleados.add(new Empleado("Barba Cortes Nathaly Marcela", LocalDate.of(2020,12,1), 9));

        return empleados;
    }


}
