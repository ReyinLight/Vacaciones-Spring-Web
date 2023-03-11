package com.Vacaciones.Vacaciones;

import com.Vacaciones.Vacaciones.Modelo.Empleado;
import org.junit.Assert;
import org.junit.Test;


import java.text.DecimalFormat;
import java.time.LocalDate;

public class EmpleadoTest {

    @Test
    public void pruebaAñosViejaLey1año(){
        Empleado e = new Empleado("Luis", LocalDate.of(2021, 1,1), 0);
        Assert.assertEquals(1,e.getAñosHastaAniversario2022(), 0);

        e = new Empleado("Luis", LocalDate.of(2021, 6,15), 0);
        Assert.assertEquals(1,e.getAñosHastaAniversario2022(), 0);

        e = new Empleado("Luis", LocalDate.of(2021, 12,31), 0);
        Assert.assertEquals(1,e.getAñosHastaAniversario2022(), 0);
    }

    @Test
    public void pruebaAñosViejaLeyMuchos(){
        Empleado e = new Empleado("Luis", LocalDate.of(2020, 1,1), 0);
        Assert.assertEquals(2,e.getAñosHastaAniversario2022(), 0);

        e = new Empleado("Luis", LocalDate.of(2019, 6,15), 0);
        Assert.assertEquals(3,e.getAñosHastaAniversario2022(), 0);

        e = new Empleado("Luis", LocalDate.of(2018, 12,31), 0);
        Assert.assertEquals(4,e.getAñosHastaAniversario2022(), 0);
    }

    @Test
    public void pruebaCalcularVacacionesViejaLey1año(){
        Empleado e = new Empleado("Luis", LocalDate.of(2021, 1,1), 0);
        Assert.assertEquals(6,e.calcularUsandoViejaLey(), 0);

        e = new Empleado("Luis", LocalDate.of(2021, 6,15), 0);
        Assert.assertEquals(6,e.calcularUsandoViejaLey(), 0);

        e = new Empleado("Luis", LocalDate.of(2021, 12,31), 0);
        Assert.assertEquals(6,e.calcularUsandoViejaLey(), 0);
    }
    @Test
    public void pruebaCalcularVacacionesViejaLeyMuchosaños(){
        // 2 años
        Empleado e = new Empleado("Luis", LocalDate.of(2020, 1,1), 0);
        Assert.assertEquals(14,e.calcularUsandoViejaLey(), 0);

        // 3 años
        e = new Empleado("Luis", LocalDate.of(2019, 6,15), 0);
        Assert.assertEquals(24,e.calcularUsandoViejaLey(), 0);

        // 4 años
        e = new Empleado("Luis", LocalDate.of(2018, 12,31), 0);
        Assert.assertEquals(36,e.calcularUsandoViejaLey(), 0);

        // 5 años
        e = new Empleado("Luis", LocalDate.of(2017, 1,1), 0);
        Assert.assertEquals(50,e.calcularUsandoViejaLey(), 0);

        // 6 años
        e = new Empleado("Luis", LocalDate.of(2016, 6,15), 0);
        Assert.assertEquals(64,e.calcularUsandoViejaLey(), 0);

        // 10 años
        e = new Empleado("Luis", LocalDate.of(2012, 12,31), 0);
        Assert.assertEquals(122,e.calcularUsandoViejaLey(), 0);

        // 11 años
        e = new Empleado("Luis", LocalDate.of(2011, 12,31), 0);
        Assert.assertEquals(138,e.calcularUsandoViejaLey(), 0);

        // 11 años
        e = new Empleado("Luis", LocalDate.of(2022 - 43, 12,31), 0);
        Assert.assertEquals(848,e.calcularUsandoViejaLey(), 0);

    }


    @Test
    public void pruebaCalcularUsandoNuevaLey(){

        // 1 mes
        Empleado e = new Empleado("Luis", LocalDate.of(2023, 2,10), 0);
        Assert.assertEquals(1, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);

        // 2 meses
        e = new Empleado("Luis", LocalDate.of(2023, 1,10), 0);
        Assert.assertEquals(2, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);

        // 5 meses
        e = new Empleado("Luis", LocalDate.of(2022, 10,10), 0);
        Assert.assertEquals(5, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);

        // 12 meses
        e = new Empleado("Luis", LocalDate.of(2022, 3,10), 0);
        Assert.assertEquals(12, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);

        // 12 meses y medio
        e = new Empleado("Luis", LocalDate.of(2021, 9,10), 0);
        Assert.assertEquals(12.9, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);

    }

    @Test
    public void vacacionesFinales(){
        // 1 año
        Empleado e = new Empleado("Luis", LocalDate.of(2023 - 1, 3,10), 0);
        Assert.assertEquals(12.0, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);

        // 2 mes
        e = new Empleado("Luis", LocalDate.of(2023- 2 , 3,10), 0);
        Assert.assertEquals(20.0, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);

        // 2 mes
        e = new Empleado("Luis", LocalDate.of(2023 - 5, 3,10), 0);
        Assert.assertEquals(56.1, Double.parseDouble(new DecimalFormat("#.#").format(e.calcularVacaciones())), 0);
    }



}
