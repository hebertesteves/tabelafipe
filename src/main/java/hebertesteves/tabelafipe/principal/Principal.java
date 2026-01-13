package hebertesteves.tabelafipe.principal;

import hebertesteves.tabelafipe.model.*;
import hebertesteves.tabelafipe.service.ConsumoApi;
import hebertesteves.tabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private ConsumoApi consumoApi = new ConsumoApi();
    private Scanner sc = new Scanner(System.in);
    private ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";

    public void exibirMenu() {
        System.out.println("==== OPÇÕES ====");
        System.out.println("Carros");
        System.out.println("Motos");
        System.out.println("Caminhões\n");

        System.out.print("Digite uma das opções para consulta: ");
        String tipoVeiculo = sc.nextLine();

        var json = consumoApi.consumirApi(URL_BASE + "/"
                + tipoVeiculo.toLowerCase().trim()
                +"/marcas");

        DadosMarca[] dadosMarca = conversor.obterDados(json, DadosMarca[].class);

        for (DadosMarca dadoMarca : dadosMarca) {
            System.out.println("Cód: " + dadoMarca.codigo()
                    + "   Descrição: " + dadoMarca.nome());
        }

        System.out.print("\nInforme o código da marca para consulta: ");
        int codigoMarca = sc.nextInt();
        sc.nextLine();

        json = consumoApi.consumirApi(URL_BASE + "/"
                + tipoVeiculo.toLowerCase().trim()
                + "/marcas/"
                + codigoMarca
                + "/modelos");

        DadosModelo dadosModelos = conversor.obterDados(json, DadosModelo.class);

        dadosModelos.modelos().forEach(modelo -> System.out.println("Cód: " + modelo.codigo()
                + "   Descrição: " + modelo.nome()));

        System.out.print("\nDigite um trecho do nome do veículo para consulta: ");
        String trechoNomeVeiculo = sc.nextLine();

        List<Modelo> modelosPorNome = dadosModelos.modelos().stream()
                        .filter(m -> m.nome().toLowerCase().trim().contains(trechoNomeVeiculo))
                                .collect(Collectors.toList());

        modelosPorNome.forEach(modelo -> System.out.println("Cód: " + modelo.codigo()
                + "   Descrição: " + modelo.nome()));

        System.out.print("\nDigite o código do modelo para consultar valores: ");
        int codigoModelo = sc.nextInt();
        sc.nextLine();

        json = consumoApi.consumirApi(URL_BASE + "/"
                + tipoVeiculo.toLowerCase().trim() +
                "/marcas/"
                + codigoMarca
                + "/modelos/"
                + codigoModelo
                + "/anos");

        DadosAno[] dadosAno = conversor.obterDados(json, DadosAno[].class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (DadosAno ano : dadosAno) {
            json = consumoApi.consumirApi(URL_BASE + "/"
                    + tipoVeiculo.toLowerCase().trim()
                    + "/marcas/"
                    + codigoMarca
                    + "/modelos/"
                    + codigoModelo
                    + "/anos/"
                    + ano.codigo());

            DadosVeiculo dadosVeiculo = conversor.obterDados(json, DadosVeiculo.class);
            veiculos.add(new Veiculo(dadosVeiculo));
        }

        System.out.println("\nTodos os veículos com os valores por ano: ");
        veiculos.forEach(System.out::println);
    }
}
