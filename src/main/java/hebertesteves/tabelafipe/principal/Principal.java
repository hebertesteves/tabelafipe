package hebertesteves.tabelafipe.principal;

import hebertesteves.tabelafipe.model.DadosMarca;
import hebertesteves.tabelafipe.model.DadosModelo;
import hebertesteves.tabelafipe.model.Modelo;
import hebertesteves.tabelafipe.service.ConsumoApi;
import hebertesteves.tabelafipe.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final String url = "https://parallelum.com.br/fipe/api/v1";
    private final Scanner sc = new Scanner(System.in);
    private final ConverteDados converteDados = new ConverteDados();

    public void exibirMenu() {
        System.out.println("==== OPÇÕES ====");
        System.out.println("Carros");
        System.out.println("Motos");
        System.out.println("Caminhãos\n");

        System.out.print("Digite uma das opções para consultar valores: ");
        String tipoVeiculo = sc.nextLine();

        var json = consumoApi.consumirApi(url + "/" + tipoVeiculo.toLowerCase().trim() +"/marcas");

        DadosMarca[] dadosMarca = converteDados.obterDados(json, DadosMarca[].class);

        for (DadosMarca dadoMarca : dadosMarca) {
            System.out.println("Cód: " + dadoMarca.codigo() + "   Descrição: " + dadoMarca.nome());
        }

        System.out.print("\nInforme o código da marca para consulta: ");
        int codigoMarca = sc.nextInt();
        sc.nextLine();

        json = consumoApi.consumirApi(url + "/" + tipoVeiculo.toLowerCase().trim() +"/marcas/" + codigoMarca + "/modelos");
        DadosModelo dadosModelos = converteDados.obterDados(json, DadosModelo.class);

        List<Modelo> modelos = dadosModelos.modelos();
        modelos.forEach(modelo -> System.out.println("Cód: " + modelo.codigo() + "   Descrição: " + modelo.nome()));
    }
}
