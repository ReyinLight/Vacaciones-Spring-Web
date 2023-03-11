package com.Vacaciones.Vacaciones.service;

import com.Vacaciones.Vacaciones.Modelo.Empleado;
import com.Vacaciones.Vacaciones.Modelo.EmpleadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Component
public class Servicios {

    @Autowired
    XlsxService excel;

    @Autowired
    EmpleadoDAO empleadoDAO;

    private String folder= "Vacaciones/cargas/";

    public String save(MultipartFile file){

        if(!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(folder + file.getOriginalFilename());
                Files.write(path, bytes);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        return "Archivo guardado correctamente";
    }


    public void crearIndex(Model model){
        model.addAttribute("mensaje", "Lista de ejemplo");
        model.addAttribute("empleados", empleadoDAO.getEmpleadosHARDCODE());
    }

    // Regresa un empleado generico, se usa solo para hacer el calculo de fecha
    public Empleado getEmpleadoGenerico(String fechaIngreso){
        String[] fechaSplit = fechaIngreso.split("-");
        return new Empleado("Empleado", LocalDate.of(Integer.parseInt(fechaSplit[0]),Integer.parseInt(fechaSplit[1]),Integer.parseInt(fechaSplit[2])), 0);
    }

    // Genera excel y muestra para guardar
    public ResponseEntity<InputStreamResource> getExcel() throws Exception{
        ByteArrayInputStream stream = excel.createExcel(empleadoDAO.getEmpleadosHARDCODE());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=empleadosNUEVO.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }
    // MODIFICA ARCHIVO REAL
    public ResponseEntity<InputStreamResource> getExcelModificado() throws Exception{
        ByteArrayInputStream stream = excel.modificarExcelWeb();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=VacacionesMODIFICADO.xlsx");

        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }


    @Deprecated
    public String getPrincipal(){
        String result = "Para usar la api, debe agregar la fecha al final de la url lo siguiente";
        result += "<br><br>";
        result += "Ejemplo:";
        result += "<br><br>";
        result += "/vacaciones?fecha=1992-08-13";
        result += "<br>---------------------------------<br>";
        result += "<br>O si no, ingresa los datos aqui<br>";

        result += "<a href=/exportar><button>Descargar Excel</button></a>";

        return result;
    }


}
