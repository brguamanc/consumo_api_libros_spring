package com.byron.desafio.principal;

import com.byron.desafio.model.Datos;
import com.byron.desafio.model.DatosLibros;
import com.byron.desafio.service.ConsumoAPI;
import com.byron.desafio.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE ="https://gutendex.com/books/" ;
    private ConsumoAPI consumoAPI= new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);
        //System.out.println(datos);

        //Top 10
        System.out.println("Top 10");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda
        System.out.println("Ingrese el nombre del libro que necesita");
        Scanner sc = new Scanner(System.in);
        var tituloLibro = sc.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search="+tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json,Datos.class);
        Optional<DatosLibros> libroBuscado=datosBusqueda.resultados().stream()
                .filter(l->l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro Encontrado");
            System.out.println(libroBuscado.get());
        }else{
            System.out.println("Libro no encontrado");
        }

        //Estadísticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d->d.numeroDeDescargas()>0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("Cantidad media de descargas "+est.getAverage());
        System.out.println("Cantidad máxima de descargas "+est.getMax());
        System.out.println("Cantida mínima de descargas "+est.getMin());
        System.out.println("Registros evaluados "+est.getCount());

    }
}
