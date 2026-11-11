# Sistema de Controle de Impressora Elgin
---

## 📘 Sobre o Projeto

O projeto consiste em um sistema desenvolvido em Java que permite controlar e executar funções de uma impressora térmica Elgin i8/i9 por meio de uma conexão com sua biblioteca específica. Sua finalidade é tornar o uso da impressora mais prático, facilitando o acesso às funções de impressão através de uma interface com login e opções de operação. A atividade foi realizada na disciplina de Programação Orientada a Objetos (POO).

---

## 🧰 Tecnologias Utilizadas

- Java: Linguagem utilizada para desenvolver o sistema.
- VS Code: Programa utilizado para escrever e executar o código.
- JNA 5.15.0: Biblioteca que será utilizada para a comunicação com a impressora.
- SDK Elgin: Bibliotecas necessárias para trabalhar com a impressora.
- Impressora Elgin i8 ou i9: Será utilizada para testar as funções de impressão e comunicação.
- GitHub: para armazenar e compartilhar o código.

---

## 📁 Estrutura do Projeto

O projeto está organizado da seguinte forma:


├── Bibliotecas E1 Impressora/ → Bibliotecas e arquivos da impressora Elgin.

├── bin/                
→ Armazena os arquivos compilados (.class) das classes Java.


│   ├── Impressora.class
│   ├── ImpressoraDLL.class
│   ├── Main.class
│   └── Usuario.class


├── lib/                       
→ Armazena as bibliotecas e dependências Java.


├── src/                       
→ Contém os códigos-fonte do projeto.
│   ├── Impressora.java
│   ├── ImpressoraDLL.java
│   ├── Main.java
│   └── Usuario.java


├── .gitignore                 
→ Define os arquivos que não serão enviados ao Git.

├── CANC_SAT.xml               → Arquivo XML de cancelamento SAT.

├── README.md                  
→ Documentação principal do projeto.

├── README_MODELO.md           → Modelo de referência para a documentação.

└── XMLSAT.xml                 
→ Arquivo XML utilizado na operação SAT.

Classes do sistema

Dentro da pasta src, estão localizadas as seguintes classes:

* Main.java: responsável por iniciar o programa, exibir o menu e executar as funcionalidades da impressora.
* Usuario.java: responsável pelo armazenamento do usuário e senha e pelo controle de acesso ao sistema.
* Impressora.java: responsável por implementar as funcionalidades relacionadas à comunicação e utilização da impressora.
* ImpressoraDLL.java: responsável pela integração com a DLL da impressora, permitindo acessar suas funções por meio do Java.

Pasta bin

A pasta bin armazena os arquivos .class, que são gerados após a compilação dos arquivos .java da pasta src. Esses arquivos representam o código compilado que pode ser executado pela máquina virtual Java (JVM).

---

## ⚙️ Instalação e Execução

1. Baixar o projeto

1. Acesse o repositório do projeto no GitHub.
2. Clique em Code → Download ZIP.
3. Extraia o arquivo baixado em uma pasta do computador.

2. Abrir o projeto no VS Code

1. Abra o Visual Studio Code.
2. Clique em File → Open Folder.
3. Selecione a pasta onde o projeto foi extraído.
4. Aguarde o carregamento dos arquivos e das pastas do projeto.

3. Configurar o ambiente

1. Verifique se o Java JDK está instalado no computador.
2. Instale a extensão Extension Pack for Java no VS Code.
3. Confira se a pasta lib contém as dependências Java necessárias.
4. Verifique se o arquivo jna-5.15.0.jar está disponível e configurado no projeto.
5. Confira se a biblioteca E1_Impressora01.dll está na pasta Bibliotecas E1 Impressora.

4. Conectar a impressora

1. Conecte a impressora térmica Elgin i8 ou i9 ao computador.
2. Ligue o equipamento.
3. Verifique se o computador reconheceu a impressora.
4. Confira se os arquivos e componentes necessários para a comunicação estão disponíveis.

5. Executar o sistema

1. No VS Code, abra a pasta src.
2. Localize e abra o arquivo Main.java.
3. Clique em Run ou em Run Java para iniciar o programa.
4. Aguarde a abertura da tela de login.
5. Informe o usuário e a senha cadastrados.

6. Utilizar o sistema

1. Após realizar o login, acesse o menu principal.
2. Escolha a operação de impressão desejada.
3. Siga as orientações apresentadas na tela.
4. Aguarde a execução da operação pela impressora Elgin.

7. Finalizar a utilização

1. Confira se a operação foi realizada corretamente.
2. Encerre o programa após concluir as atividades.
3. Desligue a impressora, caso não precise utilizá-la novamente.

---

## 🔐 Login

O sistema possui uma tela de login para permitir o acesso ao menu da impressora. O usuário tem até três tentativas para informar os dados corretamente. Caso erre, o sistema exibirá uma mensagem de erro e informará quantas tentativas ainda restam. Se errar as três vezes, o programa será encerrado.

Usuário: admin  
Senha: 1234

---

## 📋 Funcionalidades do Menu

1- Configurar conexão: define as configurações necessárias para conectar a impressora.

2- Abrir conexão: inicia a comunicação entre o sistema e a impressora.

3- Imprimir texto: envia textos para serem impressos.

4- Imprimir QR Code: gera e imprime códigos QR.

5- Imprimir código de barras: imprime códigos de barras.

6- Imprimir XML SAT: realiza a impressão de um arquivo XML SAT.

7- Imprimir XML de cancelamento SAT: imprime o documento XML referente ao cancelamento SAT.

8- Abrir gaveta Elgin: aciona a abertura da gaveta conectada à impressora Elgin.

9- Abrir gaveta: executa o comando de abertura da gaveta de dinheiro.

10- Sinal sonoro: emite um aviso sonoro pela impressora.

11- Avançar papel: movimenta o papel para frente.

12- Status da impressora: verifica as condições e o estado atual da impressora.

0- Fechar conexão e sair: encerra a comunicação com a impressora e finaliza o sistema.

---

## 🧪 Testes Realizados

Durante os testes, verificamos algumas funções do sistema:

- Login: testamos três tentativas com a senha errada e o sistema bloqueou o acesso corretamente.
- Impressão de texto: ainda não testamos na impressora física.
- QR Code: ainda não testamos a impressão.
- Código de barras: ainda não testamos a impressão.
- Bip: não testamos o sinal sonoro.
- Gaveta: não testamos a abertura da gaveta.

---

## 📚 Documentação Adicional

Manual da Impressora Elgin i9:
https://www.bztech.com.br/arquivos/manual-impressora-nao-fiscal-elgin-i9.pdf?srsltid=AYCX9vIxWFzZabE4Y87zhPrtgpGZcoL4QvCd_LhosnCmbUVYCoIh4ToK


Documentação do SDK E1_Impressora:
https://elgindevelopercommunity.github.io/group___m1.html

---

## 👥 Autores

• Allana Lopes Ferreira 
• Priscila Andrade Gonçalves Pereira 
• Sara Diniz da Silva