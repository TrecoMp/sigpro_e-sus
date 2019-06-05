# sigpro_e-sus
Sistema de Gerenciamento de Protocolos E-SUS - Produzido na Disciplina de Serviços de Redes no Curso Redes de Computadores

Projeto elaborado em 2019.1 - UFC Campus Quixadá

Utiliza
-Mysql
-Springboot
-SpringSecurity

As dependência podem ser vistas no pom.xml

Após rodar a primeira vez, para serem criadas as tabelas é necessário realizar uma inserção inicial no banco, pois todas as páginas do Servidor necessitam de autenticação para serem acessadas como pode ser visto no SecurityConfig.

\# insere o primeiro usúario no banco
\# login=admin
\# senha=sigpro

insert into pessoa (id, email, login, nome, senha, telefone) value( <--VER HIBERNATE SEQUENCE-->, 'admin@admin', 'admin', 'admin', '$2a$10$A6AxbDulhz/xc1OFn35a0OVRmpfwGzoNmHyK/EGR1iPzMlhZh1pxa', '(00) 0-0000-0000');


obs: note de verificar a tabela hibernate-sequence se for realizado no inicio os valores serão 1 e após a inserção atualizia para 2

\# alimenta a tabela role - que define as funções no SpringSecurity
insert into role(papel) value ('ROLE_USER');
insert into role(papel) value ('ROLE_ADMIN');

\# alimenta a relação pessoas_roles, definindo o usuário admin com a role admin
insert into pessoas_roles(pessoa_id, role_id) values(<id-usuario>,'ROLE_ADMIN');
  
  



######BALBURDIA#######
