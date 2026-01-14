package hebertesteves.tabelafipe.principal;

import hebertesteves.tabelafipe.model.*;
import hebertesteves.tabelafipe.service.ConsumoApi;
import hebertesteves.tabelafipe.service.ConverteDados;

import java.util.*;

public class Principal {
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final Scanner sc = new Scanner(System.in);
    private final ConverteDados conversor = new ConverteDados();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibirMenu() {
        System.out.println("==== OPÇÕES ====");
        System.out.println("Carro");
        System.out.println("Moto");
        System.out.println("Caminhão\n");

        System.out.print("Digite uma das opções para consulta: ");
        String tipoVeiculo = sc.nextLine();

        String endereco = "";
        if (tipoVeiculo.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (tipoVeiculo.toLowerCase().trim().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else if (tipoVeiculo.toLowerCase().trim().contains("caminh")) {
            endereco = URL_BASE + "caminhoes/marcas";
        } else {
            System.out.println("ERRO! Veiculo não existe");
            return;
        }

        var json = consumoApi.consumirApi(endereco);

        DadosMarca[] dadosMarca = conversor.obterDados(json, DadosMarca[].class);
        List<DadosMarca> marcas = Arrays.stream(dadosMarca)
                .sorted(Comparator.comparing(DadosMarca::codigo))
                .toList();

        marcas.forEach(marca ->
                System.out.println("Cód: " + marca.codigo() + "   Descrição: " + marca.nome())
        );

        System.out.print("\nInforme o código da marca para consulta: ");
        int codigoMarca = sc.nextInt();
        sc.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoApi.consumirApi(endereco);

        DadosModelo dadosModelos = conversor.obterDados(json, DadosModelo.class);
        List<Modelo> modelos = dadosModelos.modelos().stream()
                .sorted(Comparator.comparing(Modelo::codigo))
                .toList();

        modelos.forEach(modelo ->
                System.out.println("Cód: " + modelo.codigo() + "   Descrição: " + modelo.nome())
        );

        System.out.print("\nDigite um trecho do nome do veículo para consulta: ");
        String trechoNomeVeiculo = sc.nextLine();

        List<Modelo> modelosFiltrados = modelos.stream()
                        .filter(m -> m.nome().toLowerCase().contains(trechoNomeVeiculo.toLowerCase().trim()))
                                .toList();

        modelosFiltrados.forEach(modelo -> System.out.println("Cód: " + modelo.codigo()
                + "   Descrição: " + modelo.nome()));

        System.out.print("\nDigite o código do modelo para consultar valores: ");
        int codigoModelo = sc.nextInt();
        sc.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumoApi.consumirApi(endereco);

        DadosAno[] dadosAno = conversor.obterDados(json, DadosAno[].class);

        List<Veiculo> veiculos = new ArrayList<>();
        for (DadosAno ano : dadosAno) {
            json = consumoApi.consumirApi(endereco + "/" + ano.codigo());

            DadosVeiculo dadosVeiculo = conversor.obterDados(json, DadosVeiculo.class);
            veiculos.add(new Veiculo(dadosVeiculo));
        }

        System.out.println("\nTodos os veículos com os valores por ano: ");
        veiculos.forEach(System.out::println);
    }
}
