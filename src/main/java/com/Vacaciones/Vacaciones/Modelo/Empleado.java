package com.Vacaciones.Vacaciones.Modelo;

import lombok.*;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
public class Empleado {

    @Getter @Setter
    String nombre;
    @Getter @Setter @NonNull
    LocalDate fechaIngreso;
    @Getter @Setter
    int vacacionesTomadas;


    public double calcularVacaciones(){
        return Double.parseDouble(new DecimalFormat("#.##").format(calcularUsandoViejaLey() + calcularDesdeNuevaLey()));
    }

    // Calcula los dias de vacaciones por años completos y el proporcional al ultimo año con la NUEVA ley
    public double calcularDesdeNuevaLey() {

        double añosTotales = Period.between(fechaIngreso, LocalDate.now()).getYears();
        double añosViejaLey = getAñosHastaAniversario2022();
        double totalVacaciones = 0; // calcularUsandoViejaLey();
        int diasVacas = 12;
        int i;

        // Se recorrerán los años para evaluar los dias de vacaciones por años completos
        for (i = 0; i < 5 && i < añosTotales; i++){
            if(i >= añosViejaLey) // Si los años actuales ya superaron a los de la nueva ley, entonces se agregan los dias de vacaciones nuevos
                totalVacaciones += diasVacas;
            diasVacas += 2;
        }

        for (i = i + 1; i < añosTotales; i++){
            if(i >= añosViejaLey) // Si los años actuales ya superaron a los de la nueva ley, entonces se agregan los dias de vacaciones nuevos
                totalVacaciones += diasVacas;

            if (i % 5 == 0) // Cada 5 años se incrementan los dias de vacaciones en 2
                diasVacas += 2;
        }

        // Calcular fraccion del ultimo año
        // Si en el año actual es multiplo de 5 entonces corresponde un incremento de dias de vacaciones
        if (i % 5 == 0)
            diasVacas += 2;

        // Se calculan los dias de vacaciones proporcionales
        double diasProporcionales = (getAñosDesdeAniversario2022() * diasVacas);

        // regresa con 2 decimales
        return totalVacaciones + diasProporcionales;

    }

    // Formula que calcula los dias de vacaciones usando la vieja ley
    public double calcularUsandoViejaLey()    {

        double añosViejaLey = getAñosHastaAniversario2022();
        double totalVacaciones = 0;
        int vacacionesPorAño = 4;
        int i;

        for(i = 0; i < 4 && i < añosViejaLey ; i++){
            vacacionesPorAño += 2;
            totalVacaciones += vacacionesPorAño;
        }

        for (i = i + 1; i <= añosViejaLey; i++)
        {
            if (i % 5 == 0)
                vacacionesPorAño += 2;

            totalVacaciones += vacacionesPorAño;
        }
        return totalVacaciones;

    }

    // Obtiene años trabajados hasta el cambio de ley del 2022
    public double getAñosHastaAniversario2022() {

        double periodo = Period.between(
                       fechaIngreso,
                       LocalDate.of(
                               2022,
                               fechaIngreso.getMonth(),
                               fechaIngreso.getDayOfMonth())
                       ).getYears();

        // Se agrega esta condifion porque el metodo between puede devolver numeros negativos
        // y esto afecta el funcionamiento del resto del programa
        return periodo > 0 ? periodo : 0;
    }

    // Obtiene los años trabajados desde el cambio de ley del 2022
    // Este metodo se usa para calcular el proporcional desde el 2022,
    // solo la parte fraccionaria, por eso no se toman en cuenta los años
    public double getAñosDesdeAniversario2022() {


        Period period = Period.between(
                LocalDate.of(
                        2022, // se toma el 2022 como refeerencia pero lo importante son el dia y mes de ingreso
                        fechaIngreso.getMonth(),
                        fechaIngreso.getDayOfMonth())
                , LocalDate.now());

        /* Se multiplican los meses por (365/12) debido a los decimales
         si se dividiera 365 / 12 el resultado es 30.41666666... periodico, y al redondear se pierde un decimal
         de modo que al año no se cumplan los dias de vacaciones completos
         haciendolo de esta manera se respetan los decimales y se realiza el calculo mas precizo posible
         */
        double decimales = ((double)(period.getMonths() * (365/12) + period.getDays()) / 365);

        return decimales;
    }

}
