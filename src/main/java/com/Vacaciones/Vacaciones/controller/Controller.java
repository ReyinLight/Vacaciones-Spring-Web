package com.Vacaciones.Vacaciones.controller;


import com.Vacaciones.Vacaciones.Modelo.EmpleadoDAO;
import com.Vacaciones.Vacaciones.service.Servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//@RestController
@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    Servicios servicios;

    @Autowired
    EmpleadoDAO empleadoDao;


    @GetMapping("/")
    public String inicio(Model model){
        servicios.crearIndex(model);
        return "index";
    }
    @GetMapping("/calcular")
    public String forma(Model model){
        return "calcular";
    }

    @PostMapping("/mostrarVacaciones")
    //public String registrar(HttpServletRequest request, Model modelo){
    public String registrar(@RequestParam String fecha, Model modelo){
        modelo.addAttribute("empleado", servicios.getEmpleadoGenerico(fecha));
        return "mostrar";
    }


    @GetMapping(value="/exportarEjemplo")
    public ResponseEntity<InputStreamResource> exportAllData() throws Exception{
        return servicios.getExcel();
    }


    // vistas para para cargar un archivo
    @GetMapping("/cargar")
    public String mostrarCargar(Model model){
        return "paginaDeCarga";
    }

    @PostMapping("/cargarArchivo")
    public ResponseEntity<InputStreamResource> cargarExcel(@RequestParam("archivo")MultipartFile file, RedirectAttributes ms) throws Exception {
        servicios.save(file);
        return servicios.getExcelModificado();
    }
}
