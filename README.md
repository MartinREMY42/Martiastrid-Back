#1. setup

1. Dans une Database existante executer le script : `martiastrid_creation_tables.ddl`  
Celui-ci :
    - crée un utilsiateur app avec comme mdp app
    - crée un schéma pizza
    - crée les tables nécessaires dans le schéma pizza
    - donne les droits nécessaires à l'utilisateur app 
dans le dossier bd_doc à la racine du projet il y a 2 scripts
un de création des tables et un de population des donées

2. executer le script `martiastrid_insert_data.ddl`

3. dans le fichier `src\main\resources\application.yml` adapter 
le lien vers la base de données : replacer `5432/martiastrid_pizza` avec 
les valeurs correspondantes à la base de données préexistante.

4. si le port 8082 est déja utiliser changer le numéro de port dans `src\main\resources\application.yml`

5. executer `mvn spring-boot:run`


#2. promos possibles

- que des pizzas vegan 
- date d'anniversaire
- on est en décembre 

#3. utilisateur pour paypal

michal.borkowski-buyer@busi.eu
Labello91528

#4. problèmes connus

- utilisateur non automatiquement connecté après enregistrement
- redirection toujours vers l'acceuil après le login
- pas de vérification intelligente de la date de naissance (futur possible)

#5. divers 
nombre ingrédients max pizza perso : 6

