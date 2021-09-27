package app;

import static spark.Spark.*;

import service.ProdutoService;

public class Aplicacao {
	
	private static SerieService serieService = new SerieService();
	
    public static void main(String[] args) {
        port(5500);

        post("/serie", (request, response) -> serieService.add(request, response));

        get("/serie/:id", (request, response) -> serieService.get(request, response));

        get("/serie/update/:id", (request, response) -> serieService.update(request, response));

        get("/serie/delete/:id", (request, response) -> serieService.remove(request, response));

        get("/serie", (request, response) -> serieService.getAll(request, response));
               
    }
}