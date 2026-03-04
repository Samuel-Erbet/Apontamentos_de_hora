
CREATE TABLE `apontamentos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `codigo_parada` varchar(255) DEFAULT NULL,
  `data` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `horario_fim` time DEFAULT NULL,
  `horario_inicio` time DEFAULT NULL,
  `numero_os` varchar(255) DEFAULT NULL,
  `unidade` varchar(255) DEFAULT NULL,
  `funcionario_matricula` bigint DEFAULT NULL,
  `ultima_alteracao` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT f_matricula FOREIGN KEY (`funcionario_matricula`) REFERENCES `funcionario` (`matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;