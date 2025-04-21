1. Развернуть базу данных из дампа jewelry_dbg.sql в вашем кластере PostgreSQL:
  - psql -f jewelry_dbg.sql -U postgres  
  - psql -d jewelry_dbg -U postgres
2. Собрать и запустить проект:
mvn clean install
mvn spring-boot:run
