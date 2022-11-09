DROP TABLE IF EXISTS public.items;
DROP SEQUENCE IF EXISTS public.item_sequence;
DROP TABLE IF EXISTS public.users;
DROP SEQUENCE IF EXISTS public.user_sequence;

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255),
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_unique UNIQUE (email)
);

CREATE SEQUENCE IF NOT EXISTS public.user_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.items
(
    id bigint NOT NULL,
    available boolean,
    description character varying(255),
    name character varying(255),
    request bigint,
    owner_id bigint,
    CONSTRAINT items_pkey PRIMARY KEY (id)
--    CONSTRAINT items_fk_owner FOREIGN KEY (owner_id)
--        REFERENCES public.users (id) MATCH SIMPLE
--        ON UPDATE NO ACTION
--        ON DELETE NO ACTION
);

CREATE SEQUENCE IF NOT EXISTS public.item_sequence
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;


