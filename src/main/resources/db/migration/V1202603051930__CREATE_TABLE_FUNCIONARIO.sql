
CREATE TABLE if not exists `funcionario`  (
  `matricula` bigint NOT NULL,
  `cargo` varchar(255) DEFAULT NULL,
  `cidade` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `turno` varchar(255) DEFAULT NULL,
  `acesso` varchar(255) DEFAULT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `gestor_matricula` bigint DEFAULT NULL,
  PRIMARY KEY (`matricula`),
  KEY `FKc5eqfh2x3as6vkwamlk67578n` (`gestor_matricula`),
  CONSTRAINT `FKc5eqfh2x3as6vkwamlk67578n` FOREIGN KEY (`gestor_matricula`) REFERENCES `funcionario` (`matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;