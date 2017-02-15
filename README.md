# API Encurtadora de URL

## BACK-END
O projeto foi desenvolvido em Spring, Maven para um melhor controle das dependências, como ORM foi utilizado o Hibernate, para tornar possível testes remotos a aplicação está utilizando o Spring security, que se encarrega de tratar uma exceção de segurança  e H2 para o registro das informações.

Para a realição de testes de integração foi utilizado o Framework de Teste do Spring MVC.

## FRONT-END
O front foi feito em React visando facilitar a integração de views para o consumo da API. Em seu código foi utilizado
a sintaxe JSX.
A interface está desenvolvida de modo responsivo, para assim garantir um bom acesso para qualquer usuário.

## Passos para executar a APLICAÇÃO: 
1. Subir o front em localhost:8080;
2. instalar no navegador o plugin Allow-Control-Allow-Origin: *, pois ele permite que a view interaja com a API ambos remotamente, por questões de segurança isso por padrão é bloqueado pelos navegadores.

## Oberservações:
Para administrar os registros no H2 é necessário indicar: jdbc:h2:file:~/h2/dbShortenURL;DB_CLOSE_ON_EXIT=FALSE 
no campo jDBCURL localizado na página de login.

Dentro da pasta frontapi estão diagramas de sequência que representam os cenários atendidos pela aplicação.

O executável .jar do projeto está em: idshorten\target



