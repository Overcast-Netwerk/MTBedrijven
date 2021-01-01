


/*
	Queries om een bedrijf te maken in de database

*/

set @owner_uuid = "";
set @name = "";
set @type = 0;

INSERT INTO bedrijven (id, owner_uuid, name, balance, creation_date, disabled, type) VALUES (NULL, @owner_uuid, @name, 5000, "2021-1-1 21:55:00", 0, @type);
INSERT INTO werknemers (id, user_uuid, company_id, role, pay, hiring_date, disabled) VALUES (NULL, @owner_uuid, (SELECT id FROM bedrijven WHERE owner_uuid = @owner_uuid), 3, 100, "2021-1-1 21:55:00", 0);


/*
	Queries om een werknemer aan te maken op een bedrijf

*/

SET @user_uuid = "";
set @company_id = 1; /*Dit is de id in de bedrijven tabel in de database*/

INSERT INTO werknemers (id, user_uuid, company_id, role, pay, hiring_date, disabled) VALUES (NULL, @user_uuid, @company_id, 0, 100, "2021-1-1 21:55:00", 0);


/*
	Queries om een werknemer te beheren
*/



set @user_uuid = "";
set @company_id = 1;


/*
	Promoveren van een werknemer
	
*/

set @role = 1; /*Rangen: 0 Werknemer, 1 Team Leader, 2 Administrator, 3 Eigenaar*/
UPDATE werknemers SET role = @role WHERE user_uuid = @user_uuid AND company_id = @company_id AND disabled = 0;

/*
	Loon aanpassen

*/

set @pay = 1200;
UPDATE werknemers SET role = @role WHERE user_uuid = @user_uuid AND company_id = @company_id AND disabled = 0;



/*
	Queries voor het aanmaken en het beheren van een factuur

*/


/*
	Aanmaken
*/

set @company_id = 10;
set @klant_uuid = "";
set @price = 500;
set @description = "Spaghetti Bolognese met bladgoud"
set @creation_date = "2021-1-1 21:55:00";
set @werknemer_uuid = "";
set @factuur = 1;

INSERT INTO facturen (id, company_id, client_uuid, price, description, paid, creation_date, disabled, creator_uuid) VALUES (NULL, @company_id, @client_uuid, @price, @description, 0, @creation_date, 0, @werknemer_uuid);


/*
	Op betaald zetten
*/

UPDATE facturen SET paid = 1 WHERE client_uuid = @klant_uuid AND disabled = 0 AND company_id = @company_id AND id = @factuur;

/*
	"Verwijderen" uit de lijst
*/

UPDATE facturen SET disabled = 1 WHERE client_uuid = @klant_uuid AND disabled = 0 AND company_id = @company_id AND id = @factuur;

