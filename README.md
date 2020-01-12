# SpaceShipsBattle
## Visão geral:
     Este é uma jogo elaborado como projeto final de disciplina de POO do curso de ADS no IFPI turma de 20191
  
### Componentes
    - Luiz Araujo da Conceição Junior
    - Phelyppe Matheus Silva da Costa
  
## Jogo
    É um jogo multiplayer em rede local onde os usuarios conectam até 5 dispositivos ao servidor e fazem uma batalha 
    épica com as suas naves;
    O jogo é composto de duas aplicações:
      -Servidor em Java com Jplay (que tambem exibe o jogo);
      -Controles em java android (cada controle comanda uma nave);
  ### classes
   #### -servidor
        Main - roda todas o jogo; 
        Ip - verifica o IP do servidor que será disponibilizado ao usuario;
        ConnectionMenu - espera e realiza a comunicação inicial entre os controles e o Servidores;
        Game - Roda o jogo com um todo executando as sprite, threads, musicas entre outros arquivos

 ### Como jogar
    1. Executa main java;
        - Teclar enter;
        - Setar o numero de players 1 - 5;
        - Verificar a porta de conexão (exibidana tela);
    2. Conectar o controle;
    3. Divirtasse;
