*Stock Control API — Backend*

Esta API REST foi desenvolvida para controlar o estoque de matérias-primas e auxiliar na tomada de decisões sobre o que pode ser produzido com base nos recursos disponíveis. A ideia principal é simples: além de registrar produtos e insumos, o sistema analisa automaticamente o estoque atual e sugere quais itens valem mais a pena produzir, priorizando aqueles de maior valor.

O projeto nasceu com foco em organização, clareza de responsabilidades e facilidade de evolução. Por isso, as decisões de arquitetura e tecnologias foram pensadas para manter o código limpo, previsível e fácil de manter ao longo do tempo.

*Arquitetura*

A aplicação segue uma arquitetura em camadas inspirada na Clean Architecture. O objetivo não foi implementar o modelo de forma acadêmica, mas sim aplicar seus princípios principais na prática: separar regras de negócio, persistência e interface HTTP para evitar dependências desnecessárias.

Essa separação permite evoluir o sistema com mais segurança, facilita testes e torna o código mais compreensível para outros desenvolvedores.

*Camadas da aplicação*

*Model / Entity*
Representa os dados centrais do domínio e o mapeamento com o banco de dados. Aqui estão os conceitos principais do sistema, como produtos, matérias-primas e suas relações.

*Repository*
Responsável pelo acesso aos dados. Utiliza Spring Data JPA para lidar com persistência sem a necessidade de escrever SQL manual na maior parte dos casos.

*Service*
Onde ficam as regras de negócio. Essa camada concentra a lógica principal da aplicação, como cálculos de produção, validações mais complexas e orquestração das operações.

*Controller*
Responsável por expor os endpoints REST. Recebe as requisições do cliente, valida os dados básicos e delega o processamento para os serviços.

*DTO*
Objetos usados para entrada e saída de dados da API. Eles evitam expor diretamente as entidades do banco e permitem controlar exatamente quais informações são enviadas ou recebidas.

*Funcionalidades*
*Gestão de Produtos*

Permite cadastrar os produtos finais que podem ser produzidos ou vendidos.

Cada produto possui um valor associado

Pode depender de várias matérias-primas

Só pode ser produzido se sua composição estiver definida

*Gestão de Matérias-Primas*

Controla os insumos disponíveis no estoque.

Armazena quantidades com precisão 

Utiliza BigDecimal para evitar erros de arredondamento

Serve como base para os cálculos de viabilidade de produção

*Composição de Produtos*

Define quais insumos são necessários para fabricar cada produto, funcionando como uma receita de produção.

Relaciona produtos e matérias-primas

Permite múltiplos componentes por produto

Define a quantidade necessária de cada insumo

*Sugestão de Produção*

Este é o principal diferencial do sistema.

Com base no estoque atual, a API calcula automaticamente:

Quais produtos podem ser produzidos

Quantas unidades de cada um são viáveis

Prioridade para itens de maior valor

O objetivo é aproveitar melhor os recursos disponíveis e apoiar decisões de produção.

*Tecnologias Utilizadas*

*Java 21*
Versão estável e moderna da linguagem, com melhorias de desempenho e recursos atualizados.

*Spring Boot* 
Framework principal para construção da API REST e gerenciamento do ciclo de vida da aplicação.

*PostgreSQL*
Banco de dados relacional utilizado em produção.

*H2 Database*
Banco em memória usado durante desenvolvimento e testes locais.

*Spring Data JPA*
Abstrai a camada de persistência e reduz código repetitivo para operações comuns de banco.

*Lombok*
Diminui a quantidade de código boilerplate, como getters, setters e construtores.

*Jakarta Validation*
Utilizado para validar dados recebidos pela API antes do processamento.

*Objetivo do Projeto*

O projeto serve como base para um sistema de controle de estoque com suporte à decisão de produção. Além da funcionalidade prática, ele também demonstra boas práticas de desenvolvimento backend com Java e Spring, incluindo organização de código, separação de responsabilidades e uso de tecnologias amplamente utilizadas no mercado.

A estrutura foi pensada para ser facilmente expandida, permitindo adicionar novos módulos, regras ou integrações sem comprometer o funcionamento existente.
