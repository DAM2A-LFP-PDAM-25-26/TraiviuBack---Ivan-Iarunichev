CREATE TABLE public.clan_feed (
                                  id uuid NOT NULL,
                                  clan_id uuid NOT NULL,
                                  created_at timestamp(6) without time zone NOT NULL,
                                  media_type character varying(20),
                                  tmdb_id integer,
                                  type character varying(30) NOT NULL,
                                  user_id uuid NOT NULL,
                                  title character varying(255),
                                  release_year integer,
                                  poster_url character varying(500),
                                  CONSTRAINT clan_feed_media_type_check CHECK (((media_type)::text = ANY ((ARRAY['MOVIE'::character varying, 'TV'::character varying])::text[]))),
    CONSTRAINT clan_feed_type_check CHECK (((type)::text = ANY (
        (ARRAY[
          'WATCHED'::character varying,
          'RECOMMENDED'::character varying,
          'RATED'::character varying,
          'JOINED'::character varying,
          'LEFT'::character varying,
          'ADDED_TO_LIST'::character varying
        ])::text[]
    )))
);

ALTER TABLE public.clan_feed OWNER TO postgres;

CREATE TABLE public.clan_members (
                                     clan_id uuid NOT NULL,
                                     user_id uuid NOT NULL,
                                     joined_at timestamp(6) without time zone NOT NULL,
                                     notifications_enabled boolean NOT NULL,
                                     role character varying(20) NOT NULL
);

ALTER TABLE public.clan_members OWNER TO postgres;

CREATE TABLE public.clan_messages (
                                      id uuid NOT NULL,
                                      clan_id uuid NOT NULL,
                                      created_at timestamp(6) without time zone NOT NULL,
                                      text character varying(1000) NOT NULL,
                                      user_id uuid NOT NULL
);

ALTER TABLE public.clan_messages OWNER TO postgres;

CREATE TABLE public.clans (
                              id uuid NOT NULL,
                              created_at timestamp(6) without time zone NOT NULL,
                              invite_code character varying(20) NOT NULL,
                              members_count integer NOT NULL,
                              name character varying(100) NOT NULL,
                              owner_id uuid NOT NULL,
                              status character varying(20) NOT NULL,
                              CONSTRAINT clans_status_check CHECK (((status)::text = ANY ((ARRAY['ACTIVE'::character varying, 'DELETED'::character varying])::text[])))
);

ALTER TABLE public.clans OWNER TO postgres;

CREATE TABLE public.list_items (
                                   id uuid NOT NULL,
                                   added_at timestamp(6) with time zone NOT NULL,
                                   external_api_id character varying(255) NOT NULL,
                                   poster_url character varying(255),
                                   title character varying(255) NOT NULL,
                                   year character varying(255),
                                   list_id uuid NOT NULL
);

ALTER TABLE public.list_items OWNER TO postgres;

CREATE TABLE public.lists (
                              id uuid NOT NULL,
                              created_at timestamp(6) with time zone NOT NULL,
                              name character varying(100) NOT NULL,
                              type character varying(20) NOT NULL,
                              updated_at timestamp(6) with time zone NOT NULL,
                              user_id uuid NOT NULL
);

ALTER TABLE public.lists OWNER TO postgres;

CREATE TABLE public.users (
                              id uuid NOT NULL,
                              avatar_url character varying(255),
                              blocked boolean NOT NULL,
                              created_at timestamp(6) with time zone NOT NULL,
                              display_name character varying(100) NOT NULL,
                              email character varying(255) NOT NULL,
                              last_login_at timestamp(6) with time zone,
                              password_hash character varying(255) NOT NULL,
                              role character varying(10) NOT NULL
);

ALTER TABLE public.users OWNER TO postgres;

COPY public.users (id, avatar_url, blocked, created_at, display_name, email, last_login_at, password_hash, role) FROM stdin;
2cf05058-bf44-4242-8eec-6ac64e16863d    /uploads/avatars/f9884d22-61ab-4a92-94de-3953006dccf1-tom-and-jerry-laughing.gif   f  2026-05-14 20:16:08.066398+02  Admin  admin@traiviu.com  \N  $2a$10$iepSM2GHma4iVz6DFCV63OfQkgSDpUdHtLMWzKi79vA/IeUSLzSRG   ADMIN
51df91a9-024b-4206-8053-eb1a8e8b25e7    /uploads/avatars/61e7b563-8025-486e-b2db-1174a4325388-giphy.gif    f  2026-04-28 08:49:23.842043+02  Ivan Iarunichev    iarunichevivan@gmail.com   \N  $2a$10$zqJSA7mEcMGzmgWNXQ/db.KbtnaR2IA7v.4yi00BAEn8bgmz.CAPu   USER
\.

ALTER TABLE ONLY public.clan_feed
    ADD CONSTRAINT clan_feed_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.clan_members
    ADD CONSTRAINT clan_members_pkey PRIMARY KEY (clan_id, user_id);

ALTER TABLE ONLY public.clan_messages
    ADD CONSTRAINT clan_messages_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.clans
    ADD CONSTRAINT clans_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.list_items
    ADD CONSTRAINT list_items_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);

ALTER TABLE ONLY public.clans
    ADD CONSTRAINT uknpmk9wr8qexy29hbt6cvnk76f UNIQUE (invite_code);

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.list_items
    ADD CONSTRAINT fk9qo4efm2sp1p91w6x4mlgqh5m FOREIGN KEY (list_id) REFERENCES public.lists(id);

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT fke59kv852m4k3g8kmefph4i3kx FOREIGN KEY (user_id) REFERENCES public.users(id);