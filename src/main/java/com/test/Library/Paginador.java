package com.test.Library;

import java.util.List;
import java.util.stream.Collectors;

public class Paginador<T> {

	
    private int pages = 5;
    private final int PageNav = 5;
    private int CurrentPage;
    private final String beforePage = " &laquo; Anterior ";
    private final String pageLink = " Siguiente &raquo; ";
    private final String firstPage = " &laquo; Primero ";
    private final String lastPage = " Último &raquo; ";
    private String browsePage = "";

    public Object[] paginator(List<T> table, int pagina, int registros,
            String action, String host) {
        pages = registros > 0 ? registros : pages;
        CurrentPage = pagina == 0 ? 1 : pagina;

        int pagi_totalReg = table.size();
        double valor1 = Math.ceil((double) pagi_totalReg / (double) pages);
        int pagi_totalPags = (int) Math.ceil(valor1);
        if (CurrentPage != 1) {           
            int pagi_url = 1; 
            browsePage += "<a class='btn btn-default' href='" + host + action
                    + "?page=" + pagi_url + "&registros=" + pages + "'>" + firstPage + "</a>";
            

            pagi_url = CurrentPage - 1;
            browsePage += "<a class='btn btn-default' href='" + host + action
                    + "?page=" + pagi_url + "&registros=" + pages + "'>" + beforePage + " </a>";
        }
        float valor = (PageNav / 2);
        int pagi_nav_intervalo = Math.round(valor);        
        int pagi_nav_desde = CurrentPage - pagi_nav_intervalo;       
        int pagi_nav_hasta = CurrentPage + pagi_nav_intervalo;
         if (pagi_nav_desde < 1){ 
            pagi_nav_hasta -= (pagi_nav_desde - 1);            
            pagi_nav_desde = 1;
         }
 
        if (pagi_nav_hasta > pagi_totalPags) {
            pagi_nav_desde -= (pagi_nav_hasta - pagi_totalPags);           
            pagi_nav_hasta = pagi_totalPags;
            if (pagi_nav_desde < 1) {
                pagi_nav_desde = 1;
            }
        }
        for (int pagi_i = pagi_nav_desde; pagi_i <= pagi_nav_hasta; pagi_i++){
            if (pagi_i == CurrentPage){                
                browsePage += "<span class='btn btn-default' disabled='disabled'>" + pagi_i + "</span>";
            }else{                
                browsePage += "<a class='btn btn-default' href='" + host
                        + action + "?page=" + pagi_i + "&registros=" + pages + "'>"
                        + pagi_i + " </a>";
            }
        }
         if (CurrentPage < pagi_totalPags){             
            int pagi_url = CurrentPage + 1;
            browsePage += "<a class='btn btn-default' href='" + host + action
                    + "?page=" + pagi_url + "&registros=" + pages + "'>"
                    + pageLink + "</a>";
                          
            pagi_url = pagi_totalPags; 
            browsePage += "<a class='btn btn-default' href='" + host
                    + action + "?page=" + pagi_url + "&registros=" + pages + "'>"
                    + lastPage + "</a>";
         }
        int pagi_inicial = (CurrentPage - 1) * pages;         
        List<T> query = table.stream().skip(pagi_inicial).limit(pages)
                .collect(Collectors.toList());
         String pagi_info = "Resultados de <b>" + CurrentPage + "</b> al <b>" + pagi_totalPags + "</b> de <b>"
                + pagi_totalReg + "</b> <b>/" + pages + "</b>";
        Object[] data = {pagi_info, browsePage, query};
        return data;
    }
}
