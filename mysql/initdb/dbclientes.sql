-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
	id bigint not null auto_increment, 
	documento varchar(14) not null, 
	nome varchar(255) not null, 
	email varchar(255), 
	endereco varchar(255), 
	tipo_documento varchar(4) not null, 
	primary key (id)
) engine=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


insert into cliente (documento, nome, email, endereco, tipo_documento) values 
	('42473649014', 'Juan Cauê Thiago das Neves', 'juancauethiagodasneves@ornatopresentes.com.br', 'Rua Anacleto Vitorino, 585 - Centro - Cabedelo-PB', 'CPF'),
	('04396563388', 'Otávio Osvaldo Viana', 'otavioosvaldoviana@prositeweb.com.br', 'Avenida Raulina Fonseca Pascoal, 550 - Setor Central - Catalão-GO', 'CPF'),
	('50194852849', 'Cauã Luís Nathan da Rocha', 'ccaualuisnathandarocha@bakerhughes.com', 'Rua Vinte e Seis, 877 - Jardim Mariana - Várzea Grande-MT', 'CPF'),
	('15175573721', 'Nathan Caleb Silveira', 'nathancalebsilveira@agsolve.com.br', 'Rua JC-04, 145 - Olímpico - Boa Vista-RR', 'CPF'),
	('33119294977', 'Rita Valentina Porto', 'ritavalentinaporto@belaggiovini.com.br', 'Rua Luzilândia, 756 - Parque Amazônia - Goiânia-GO', 'CPF'),
	('62509968777', 'Manuela Agatha da Costa', 'manuelaagathadacosta@agsolve.com.br', 'Caminho 54-Quadra F, 366 - Cajazeiras - Salvador-BA', 'CPF'),
	('35281254545', 'Kaique Jorge Porto', 'kaiquejorgeporto@roche.com', 'Rua Adelina Lopes, 878 - Nova Brasília - Salvador-BA', 'CPF'),
	('83118882999', 'Henrique Rafael Baptista', 'hhenriquerafaelbaptista@robertacorrea.com', 'Rua Jerusalém, 495 - Setor Parque Tremendão - Goiânia-GO', 'CPF'),
	('99036162521', 'Mirella Emily Gabriela Campos', 'mmirellaemilygabrielacampos@dlh.de', 'Travessa Quinze de Março, 487 - Nova República II - Santarém-PA', 'CPF'),
	('94815123551', 'Bento Rodrigo Tomás Jesus', 'bentorodrigotomasjesus@bigfoot.com', 'Rua Magalhães Filho, 354 - Nossa Senhora das Graças - Teresina-PI', 'CPF'),
	('76178639260', 'Nicolas Leonardo Bento Sales', 'nicolasleonardobentosales@balaiofilmes.com.br', 'Rua Boa Ventura, 602 - Dom Pedro II - Anápolis-GO', 'CPF'),
	('88333104509', 'Tânia Silvana Rocha', 'taniasilvanarocha@rotadasbandeiras.com.br', 'Rua São Manoel, 792 - Santiago - Ji-Paraná-RO', 'CPF'),
	('41109374500', 'Ian Eduardo Ribeiro', 'iianeduardoribeiro@ggm.com.br', 'Rua Vila Lobos, 312 - Três Vendas - Pelotas-RS', 'CPF'),
	('01860328032', 'Raul Cauê Leonardo da Rosa', 'raulcaueleonardodarosa@zf-lenksysteme.com', 'Servidão Mirante, 717 - Ribeirão da Ilha - Florianópolis-SC', 'CPF'),
	('71867640457', 'Silvana Nina Vieira', 'silvananinavieira@tradevalle.com.br', 'Travessa São Jorge, 250 - São Bento - Boa Vista-RR', 'CPF'),
	('40748551786', 'Natália Isis Nogueira', 'nataliaisisnogueira@img.com.br', 'Avenida Alvaro Carvalho Barbosa, 965 - Novo Horizonte - Macapá-AP', 'CPF'),
	('75962950291', 'Brenda Larissa Francisca da Cruz', 'brendalarissafranciscadacruz@tecvap.com.br', 'Rua Francisco de Castro, 182 - Fazendinha - Campo Largo-PR', 'CPF'),
	('23855751404', 'Ayla Regina Cristiane Peixoto', 'aylareginacristianepeixoto@monsanto.com', 'Rua Praia dos Ilhéus, 967 - Campestre - São Leopoldo-RS', 'CPF'),
	('14722993530', 'Yago Lorenzo Ferreira', 'yagolorenzoferreira@bidoul.eng.br', 'Rua B- 1, 252 - Morada do Ouro - Setor Norte - Cuiabá-MT', 'CPF'),
	('70018535348', 'Oliver Luan Severino Ferreira', 'oliverluanseverinoferreira@oticascarol.com.br', 'Rua Dom Pedro II, 188 - Loteamento Cellos - Rondonópolis-MT', 'CPF'),
	('51492658000107', 'Calebe e Guilherme Pizzaria Delivery ME', 'diretoria@calebepizzaria.com.br', 'Praça Júlio Rodrigues, 588 - Araras-SP', 'CNPJ'),
	('42725455000114', 'Tânia e Marcela Fotografias ME', 'producao@taniaemarcelafotografiasme.com.br', 'Rua Moacyr Matthiesen, 763 - Araras-SP', 'CNPJ'),
	('33657619000122', 'Andrea e Luiz Marcenaria ME', 'contato@andreaeluizmarcenariame.com.br', 'Alameda Francisco Torres, 440 - Jardim Jequitibá, Presidente Prudente-SP', 'CNPJ'),
	('41416814000199', 'Fabiana e Lorena Eletrônica Ltda', 'tesouraria@fabianaelorenaeletronicaltda.com.br', 'Rua Antônio Barreto, 542 - Jardim Julieta, Poá-SP', 'CNPJ'),
	('13197486000141', 'Theo e Ruan Alimentos ME', 'faleconosco@theoalimentos.com.br', 'Rua Constantino Gaito, 192 - Jardim Marilu - São Paulo-SP', 'CNPJ');

