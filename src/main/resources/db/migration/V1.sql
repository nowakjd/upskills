CREATE TABLE speaker (
   id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   first_name VARCHAR(50),
   last_name VARCHAR(50),
   phone_number VARCHAR(20),
   email VARCHAR(50),
   bio TEXT,
   status VARCHAR(20),
   CONSTRAINT pk_speaker PRIMARY KEY (id)
);

CREATE TABLE conference (
  id UUID NOT NULL,
   name VARCHAR(250) NOT NULL,
   title VARCHAR(250) NOT NULL,
   number_of_places INTEGER NOT NULL,
   status VARCHAR(30),
   amount DECIMAL,
   currency VARCHAR(3),
   start_date TIMESTAMP WITHOUT TIME ZONE,
   end_date TIMESTAMP WITHOUT TIME ZONE,
   CONSTRAINT pk_conference PRIMARY KEY (id)
);