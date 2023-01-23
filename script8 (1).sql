SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema projekt
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema projekt
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `projekt` DEFAULT CHARACTER SET utf8 ;
USE `projekt` ;

-- -----------------------------------------------------
-- Table `projekt`.`Uzytkownik`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`Uzytkownik` (
  `idUzytkownika` INT(5) NOT NULL AUTO_INCREMENT,
  `imie` VARCHAR(45) NOT NULL,
  `nazwisko` VARCHAR(45) NOT NULL,
  `nickname` VARCHAR(45) NOT NULL,
  `dataZalozeniaKonta` DATETIME NOT NULL,
  `uprawnieniaAdministratora` TINYINT NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `email` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`idUzytkownika`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projekt`.`Adres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`Adres` (
  `idAdresu` INT NOT NULL AUTO_INCREMENT,
  `miasto` VARCHAR(45) NOT NULL,
  `kodPocztowy` INT NOT NULL,
  `ulica` VARCHAR(45) NOT NULL,
  `nrBudynku` VARCHAR(5) NOT NULL,
  `nrLokalu` INT NULL,
  PRIMARY KEY (`idAdresu`))
ENGINE = InnoDB
COMMENT = '		';


-- -----------------------------------------------------
-- Table `projekt`.`Magazyn`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`Magazyn` (
  `idMagazynu` int NOT NULL AUTO_INCREMENT,
  `pojemnosc` INT NOT NULL,
  `Adres_idAdres` INT NOT NULL,
  PRIMARY KEY (`idMagazynu`, `Adres_idAdres`),
  INDEX `fk_Magazyn_Adres1_idx` (`Adres_idAdres` ASC) VISIBLE,
  CONSTRAINT `fk_Magazyn_Adres1`
    FOREIGN KEY (`Adres_idAdres`)
    REFERENCES `projekt`.`Adres` (`idAdresu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projekt`.`SposobRealizacji`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`SposobRealizacji` (
  `idSposobRealizacji` int NOT NULL AUTO_INCREMENT,
  `sposReal` VARCHAR(20) NOT NULL,
  `koszt` FLOAT NOT NULL,
  `czasWysylki` INT NULL,
  `wniesienie` TINYINT NULL,
  PRIMARY KEY (`idSposobRealizacji`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projekt`.`Zamowienie`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`Zamowienie` (
  `idZamowienia` int NOT NULL AUTO_INCREMENT ,
  `dataGodzina` DATETIME NOT NULL,
  `uwagi` VARCHAR(45) NOT NULL,
  `Adres_idAdres` INT NOT NULL,
  `Sposob_Realizacji_idSposob_Realizacji` INT NOT NULL,
  `Uzytkownik_idUzytkownik` INT NOT NULL,
  PRIMARY KEY (`idZamowienia`, `Adres_idAdres`, `Sposob_Realizacji_idSposob_Realizacji`),
  INDEX `fk_Zamowienie_Adres1_idx` (`Adres_idAdres` ASC) VISIBLE,
  INDEX `fk_Zamowienie_Sposob_Realizacji1_idx` (`Sposob_Realizacji_idSposob_Realizacji` ASC) VISIBLE,
  INDEX `fk_Zamowienie_Użytkownik1_idx` (`Uzytkownik_idUzytkownik` ASC) VISIBLE,
  CONSTRAINT `fk_Zamowienie_Adres1`
    FOREIGN KEY (`Adres_idAdres`)
    REFERENCES `projekt`.`Adres` (`idAdresu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Zamowienie_Sposob_Realizacji1`
    FOREIGN KEY (`Sposob_Realizacji_idSposob_Realizacji`)
    REFERENCES `projekt`.`SposobRealizacji` (`idSposobRealizacji`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Zamowienie_Użytkownik1`
    FOREIGN KEY (`Uzytkownik_idUzytkownik`)
    REFERENCES `projekt`.`Uzytkownik` (`idUzytkownika`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projekt`.`Producent`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`Producent` (
  `idProducent` int NOT NULL AUTO_INCREMENT,
  `nazwaProducenta` VARCHAR(25) NOT NULL,
  `kraj` VARCHAR(30) NULL,
  `opisProducenta` VARCHAR(300) NULL,
  PRIMARY KEY (`idProducent`))
ENGINE = InnoDB;




-- -----------------------------------------------------
-- Table `projekt`.`Kategoria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`Kategoria` (
  `idKategoria` INT(20) NOT NULL AUTO_INCREMENT,
  `nazwaKategorii` VARCHAR(45) NOT NULL,
  `opisKategorii` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`idKategoria`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projekt`.`Produkt`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`Produkt` (
  `idProdukt` int NOT NULL AUTO_INCREMENT,
  `nazwaProduktu` VARCHAR(100) NOT NULL,
  `cena` FLOAT NOT NULL,
  `opis` VARCHAR(1000) NOT NULL,
  `masa` FLOAT NULL,
  `Kategoria_idKategoria` INT NOT NULL,
  `Producent_idProducent` INT NOT NULL,
  `LiczbaSztuk` INT NOT NULL,
  `nazwaObrazka` VARCHAR(45) NULL,
  PRIMARY KEY (`idProdukt`, `Kategoria_idKategoria`, `Producent_idProducent`),
  INDEX `fk_Produkt_Kategoria1_idx` (`Kategoria_idKategoria` ASC) VISIBLE,
  INDEX `fk_Produkt_Producent1_idx` (`Producent_idProducent` ASC) VISIBLE,
  CONSTRAINT `fk_Produkt_Kategoria1`
    FOREIGN KEY (`Kategoria_idKategoria`)
    REFERENCES `projekt`.`Kategoria` (`idKategoria`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Produkt_Producent1`
    FOREIGN KEY (`Producent_idProducent`)
    REFERENCES `projekt`.`Producent` (`idProducent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `projekt`.`SztukiProduktu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`SztukiProduktu` (
  `Zamowienie_idZamowienie` INT NOT NULL,
  `Produkt_idProdukt` INT NOT NULL,
  INDEX `fk_Sztuki_produktu_Zamowienie1_idx` (`Zamowienie_idZamowienie` ASC) VISIBLE,
  INDEX `fk_SztukiProduktu_Produkt1_idx` (`Produkt_idProdukt` ASC) VISIBLE,
  CONSTRAINT `fk_Sztuki_produktu_Zamowienie1`
    FOREIGN KEY (`Zamowienie_idZamowienie`)
    REFERENCES `projekt`.`Zamowienie` (`idZamowienia`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SztukiProduktu_Produkt1`
    FOREIGN KEY (`Produkt_idProdukt`)
    REFERENCES `projekt`.`Produkt` (`idProdukt`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `projekt`.`ProduktMagazyn`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `projekt`.`ProduktMagazyn` (
  `Magazyn_idMagazynu` INT NOT NULL,
  `Produkt_idProdukt` INT NOT NULL,
  INDEX `fk_ProduktMagazyn_Magazyn1_idx` (`Magazyn_idMagazynu` ASC) VISIBLE,
  INDEX `fk_ProduktMagazyn_Produkt1_idx` (`Produkt_idProdukt` ASC) VISIBLE,
  CONSTRAINT `fk_ProduktMagazyn_Magazyn1`
    FOREIGN KEY (`Magazyn_idMagazynu`)
    REFERENCES `projekt`.`Magazyn` (`idMagazynu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProduktMagazyn_Produkt1`
    FOREIGN KEY (`Produkt_idProdukt`)
    REFERENCES `projekt`.`Produkt` (`idProdukt`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
