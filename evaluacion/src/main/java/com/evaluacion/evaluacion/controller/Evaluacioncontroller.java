package com.evaluacion.evaluacion.controller;

import com.evaluacion.evaluacion.model.Estudiante;
import com.evaluacion.evaluacion.model.Pregunta;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class Evaluacioncontroller {  // <-- nombre con C mayúscula para seguir convención

    private final List<Pregunta> preguntas = new ArrayList<>();
    private final Map<String, Estudiante> estudiantes = new HashMap<>(); // documento -> estudiante

    public Evaluacioncontroller() {
        preguntas.add(new Pregunta(1, "¿Qué tipo de onda es el sonido?",
                List.of("Electromagnética", "Mecánica", "Transversal", "Gravitacional"), 1));
        preguntas.add(new Pregunta(2, "¿Qué onda no necesita un medio para propagarse?",
                List.of("Sonido", "Microondas", "Sónica", "Sísmica"), 1));
        preguntas.add(new Pregunta(3, "¿Qué ley explica la gravitación universal?",
                List.of("Ley de Coulomb", "Ley de Hooke", "Ley de Newton", "Ley de Gauss"), 2));
        preguntas.add(new Pregunta(4, "¿Cuál es la frecuencia de una onda que vibra 50 veces por segundo?",
                List.of("50 Hz", "0.5 Hz", "5 Hz", "500 Hz"), 0));
        preguntas.add(new Pregunta(5, "¿Qué tipo de onda es la luz?",
                List.of("Mecánica", "Electromagnética", "Sonora", "Longitudinal"), 1));
        preguntas.add(new Pregunta(6, "¿Qué parte del espectro electromagnético es visible?",
                List.of("Infrarrojo", "Ultravioleta", "Luz visible", "Microondas"), 2));
        preguntas.add(new Pregunta(7, "¿Qué instrumento mide la frecuencia de una onda?",
                List.of("Frecuencímetro", "Amperímetro", "Voltímetro", "Osciloscopio"), 0));
        preguntas.add(new Pregunta(8, "¿Cuál es la unidad de la frecuencia?",
                List.of("Hertz", "Newton", "Julio", "Voltio"), 0));
        preguntas.add(new Pregunta(9, "¿Qué tipo de movimiento tiene un péndulo simple?",
                List.of("Rectilíneo", "Circular", "Armónico", "Uniforme"), 2));
        preguntas.add(new Pregunta(10, "¿Qué fuerza actúa sobre un péndulo?",
                List.of("Electromagnética", "Gravitatoria", "Fricción", "Magnética"), 1));
        preguntas.add(new Pregunta(11, "¿Qué pasa con la frecuencia si aumenta la longitud del péndulo?",
                List.of("Aumenta", "Disminuye", "No cambia", "Se duplica"), 1));
        preguntas.add(new Pregunta(12, "¿Cuál es la constante de gravitación universal?",
                List.of("6.67x10^-11 N·m²/kg²", "9.8 m/s²", "1.6x10^-19 C", "3x10^8 m/s"), 0));
        preguntas.add(new Pregunta(13, "¿Qué describe una onda transversal?",
                List.of("Partículas paralelas a la propagación", "Partículas perpendiculares", "Ambas", "Ninguna"), 1));
        preguntas.add(new Pregunta(14, "¿Qué determina la energía de una onda electromagnética?",
                List.of("Amplitud", "Frecuencia", "Longitud de onda", "Velocidad"), 1));
        preguntas.add(new Pregunta(15, "¿Qué tipo de onda es una microonda?",
                List.of("Mecánica", "Longitudinal", "Electromagnética", "Gravitatoria"), 2));
    }

    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estudiante", new Estudiante("", "", new ArrayList<>(), 0));
        // Seleccionar aleatoriamente 5 preguntas
        List<Pregunta> seleccionadas = new ArrayList<>(preguntas);
        Collections.shuffle(seleccionadas);
        model.addAttribute("preguntas", seleccionadas.subList(0, 5));
        return "index";  // index.html debe estar en resources/templates/
    }

    @PostMapping("/evaluar")
    public String evaluar(@RequestParam String nombre,
                          @RequestParam String documento,
                          @RequestParam Map<String, String> respuestas,
                          Model model) {

        if (estudiantes.containsKey(documento)) {
            Estudiante yaRespondio = estudiantes.get(documento);
            model.addAttribute("mensaje", "Ya realizaste la evaluación. Tu nota fue: " + yaRespondio.getNota());
            return "resultado";
        }

        List<Map<String, Object>> resultadoPreguntas = new ArrayList<>();
        int correctas = 0;

        for (Pregunta p : preguntas) {
            String clave = "p" + p.getId();
            if (respuestas.containsKey(clave)) {
                int seleccion = Integer.parseInt(respuestas.get(clave));
                boolean esCorrecta = seleccion == p.getCorrecta();
                if (esCorrecta) correctas++;

                Map<String, Object> resultado = new HashMap<>();
                resultado.put("pregunta", p.getEnunciado());
                resultado.put("opciones", p.getOpciones());
                resultado.put("seleccion", seleccion);
                resultado.put("correcta", p.getCorrecta());
                resultado.put("esCorrecta", esCorrecta);

                resultadoPreguntas.add(resultado);
            }
        }

        int nota =  correctas * 10;
        Estudiante nuevo = new Estudiante(nombre, documento, new ArrayList<>(), nota);
        estudiantes.put(documento, nuevo);

        model.addAttribute("nota", nota);
        model.addAttribute("correctas", correctas);
        model.addAttribute("mensaje", "Gracias por presentar la evaluación.");
        model.addAttribute("respuestas", resultadoPreguntas);

        return "resultado";
    }


    @GetMapping("/informe")
    public String verInforme(Model model) {
        model.addAttribute("estudiantes", estudiantes.values());
        return "informe";  // informe.html en templates
    }
}
