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
    CONSTRAINT clan_feed_type_check CHECK (((type)::text = ANY ((ARRAY['WATCHED'::character varying, 'RECOMMENDED'::character varying, 'RATED'::character varying, 'JOINED'::character varying, 'LEFT'::character varying, 'ADDED_TO_LIST'::character varying])::text[])))
);


ALTER TABLE public.clan_feed OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 24910)
-- Name: clan_members; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clan_members (
                                     clan_id uuid NOT NULL,
                                     user_id uuid NOT NULL,
                                     joined_at timestamp(6) without time zone NOT NULL,
                                     notifications_enabled boolean NOT NULL,
                                     role character varying(20) NOT NULL
);


ALTER TABLE public.clan_members OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 24920)
-- Name: clan_messages; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clan_messages (
                                      id uuid NOT NULL,
                                      clan_id uuid NOT NULL,
                                      created_at timestamp(6) without time zone NOT NULL,
                                      text character varying(1000) NOT NULL,
                                      user_id uuid NOT NULL
);


ALTER TABLE public.clan_messages OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 24932)
-- Name: clans; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 222 (class 1259 OID 16647)
-- Name: list_items; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 220 (class 1259 OID 16600)
-- Name: lists; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lists (
                              id uuid NOT NULL,
                              created_at timestamp(6) with time zone NOT NULL,
                              name character varying(100) NOT NULL,
                              type character varying(20) NOT NULL,
                              updated_at timestamp(6) with time zone NOT NULL,
                              user_id uuid NOT NULL
);


ALTER TABLE public.lists OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16611)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

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

--
-- TOC entry 5063 (class 0 OID 24896)
-- Dependencies: 223
-- Data for Name: clan_feed; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clan_feed (id, clan_id, created_at, media_type, tmdb_id, type, user_id, title, release_year, poster_url) FROM stdin;
065ec129-a9c1-4c6d-8d62-3fae45d07c0a	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-30 13:45:37.948325	\N	\N	JOINED	3aeaa768-171a-4706-b88a-bff2c5b2b982	\N	\N	\N
aa821016-bf05-47b3-833e-a001d7802fd3	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-30 15:06:59.933775	\N	\N	LEFT	3aeaa768-171a-4706-b88a-bff2c5b2b982	\N	\N	\N
dfd068c1-bc1e-4890-9eaa-0a85581b2468	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-30 15:15:42.480928	\N	\N	JOINED	3aeaa768-171a-4706-b88a-bff2c5b2b982	\N	\N	\N
ba49992b-0c61-46e1-ac60-d1d4d425b1b9	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-30 20:14:56.848252	\N	\N	RECOMMENDED	51df91a9-024b-4206-8053-eb1a8e8b25e7	\N	\N	\N
42b9aedf-694b-427a-b0d0-aee840005e72	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-30 20:27:49.967985	TV	124364	RECOMMENDED	51df91a9-024b-4206-8053-eb1a8e8b25e7	FROM	2022	https://image.tmdb.org/t/p/w500/pRtJagIxpfODzzb0T0NAvZSzErC.jpg
efdaeb37-2ad0-4b17-bf98-9f6659b4f98d	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-30 20:36:59.889051	TV	124364	RECOMMENDED	51df91a9-024b-4206-8053-eb1a8e8b25e7	FROM	2022	https://image.tmdb.org/t/p/w500/pRtJagIxpfODzzb0T0NAvZSzErC.jpg
b6f4543f-d8bb-47fd-b62c-88b6e44170e4	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-30 20:50:00.801957	TV	124364	RECOMMENDED	51df91a9-024b-4206-8053-eb1a8e8b25e7	FROM	2022	https://image.tmdb.org/t/p/w500/pRtJagIxpfODzzb0T0NAvZSzErC.jpg
91fd2e50-61b6-4875-826e-5d54c6709138	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 10:55:14.253153	MOVIE	1318447	RECOMMENDED	51df91a9-024b-4206-8053-eb1a8e8b25e7	Depredador dominante	2026	https://image.tmdb.org/t/p/w500/mV7uFS1U8iP0p4CcXZp4znBVpch.jpg
f8acd9fc-f847-4dfa-85a5-44fd44c574e5	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 11:40:47.306391	MOVIE	687163	RECOMMENDED	3aeaa768-171a-4706-b88a-bff2c5b2b982	Proyecto Salvación	2026	https://image.tmdb.org/t/p/w500/7lwOTxajURKEWO6gI370NTrVdBO.jpg
25dd59a8-6198-49c8-9fbb-62857c508ec8	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 19:46:39.190846	TV	76479	RECOMMENDED	51df91a9-024b-4206-8053-eb1a8e8b25e7	The Boys	2019	https://image.tmdb.org/t/p/w500/5kgY14oisiHcJ4zq0Xgq1e97PHm.jpg
ead4be6b-284a-402a-b754-360188cc62a5	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 19:47:33.109764	MOVIE	687163	RECOMMENDED	3aeaa768-171a-4706-b88a-bff2c5b2b982	Proyecto Salvación	2026	https://image.tmdb.org/t/p/w500/7lwOTxajURKEWO6gI370NTrVdBO.jpg
42a58bac-955c-4ade-96bd-61d3e2b20803	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-06 17:39:13.379627	\N	\N	JOINED	a9d79d86-6e32-4513-b3b8-659ba07eeb8c	\N	\N	\N
2f15d122-66c5-4c88-af23-874a422333ec	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-06 17:39:30.097164	TV	76479	RECOMMENDED	a9d79d86-6e32-4513-b3b8-659ba07eeb8c	The Boys	2019	https://image.tmdb.org/t/p/w500/5kgY14oisiHcJ4zq0Xgq1e97PHm.jpg
\.


--
-- TOC entry 5064 (class 0 OID 24910)
-- Dependencies: 224
-- Data for Name: clan_members; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clan_members (clan_id, user_id, joined_at, notifications_enabled, role) FROM stdin;
6dfbf96c-34f4-454c-868b-b989c688ad7b	51df91a9-024b-4206-8053-eb1a8e8b25e7	2026-04-29 17:18:14.429043	t	OWNER
6dfbf96c-34f4-454c-868b-b989c688ad7b	3aeaa768-171a-4706-b88a-bff2c5b2b982	2026-04-30 15:15:42.471654	t	MEMBER
6dfbf96c-34f4-454c-868b-b989c688ad7b	a9d79d86-6e32-4513-b3b8-659ba07eeb8c	2026-05-06 17:39:13.367188	t	MEMBER
\.


--
-- TOC entry 5065 (class 0 OID 24920)
-- Dependencies: 225
-- Data for Name: clan_messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clan_messages (id, clan_id, created_at, text, user_id) FROM stdin;
f218c0c5-a39d-4690-bee6-a3efc12bb76a	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 11:09:20.392587	Hola	51df91a9-024b-4206-8053-eb1a8e8b25e7
c2db686d-ea6d-4921-b911-5d116781e0ce	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 11:44:31.545671	Buenas	3aeaa768-171a-4706-b88a-bff2c5b2b982
c4768cde-7e78-4428-8563-0c0cfc3b5bf8	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 12:04:31.718249	Prueba	51df91a9-024b-4206-8053-eb1a8e8b25e7
4ce66db5-1d75-4b31-9de5-0f32bcacc08c	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 12:07:25.527084	Prueba 2	3aeaa768-171a-4706-b88a-bff2c5b2b982
a67554a8-d622-4a2c-b151-d801ec507281	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 12:11:12.122834	Prueba 3	51df91a9-024b-4206-8053-eb1a8e8b25e7
d170dae6-c33a-46ee-8819-0a4ed5334c02	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-01 19:46:28.920084	Prueba4	51df91a9-024b-4206-8053-eb1a8e8b25e7
fcf3f29b-7893-4718-93b7-cdf5aa3969d8	6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-05-06 17:39:49.775266	ola	a9d79d86-6e32-4513-b3b8-659ba07eeb8c
\.


--
-- TOC entry 5066 (class 0 OID 24932)
-- Dependencies: 226
-- Data for Name: clans; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clans (id, created_at, invite_code, members_count, name, owner_id, status) FROM stdin;
6dfbf96c-34f4-454c-868b-b989c688ad7b	2026-04-29 17:18:14.39838	EF30A002	3	Cinéfilos Oviedo	51df91a9-024b-4206-8053-eb1a8e8b25e7	ACTIVE
\.


--
-- TOC entry 5062 (class 0 OID 16647)
-- Dependencies: 222
-- Data for Name: list_items; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.list_items (id, added_at, external_api_id, poster_url, title, year, list_id) FROM stdin;
d3d5804f-af13-4a7a-a47a-8bd9044dd64f	2026-04-10 15:40:07.489291+02	875828	https://image.tmdb.org/t/p/w500/l9S8MLUyi2diTc2Gf3KDAOQTuVx.jpg	Peaky Blinders: El hombre inmortal	2026	428436ee-ed1f-4070-b86b-1101a88e7be8
bcf8487b-b7b8-41a7-ad68-c2d6faae4a8d	2026-04-10 15:40:28.363436+02	1236153	https://image.tmdb.org/t/p/w500/9zBNg8koM3fjUTPT7QeJHuGG2r9.jpg	Sin piedad	2026	428436ee-ed1f-4070-b86b-1101a88e7be8
476ca84e-d830-4269-a3fa-f33001e69978	2026-04-10 15:40:37.742762+02	1297842	https://image.tmdb.org/t/p/w500/l8Y8o3RpFg54gmQQ8GVaxVroFmY.jpg	GOAT: Como cabras	2026	428436ee-ed1f-4070-b86b-1101a88e7be8
731872a7-b036-43d9-8529-ef019fd2f85c	2026-04-10 15:41:10.265515+02	1265609	https://image.tmdb.org/t/p/w500/5C18uOTbDZRvVIZcVRO747k2fUi.jpg	Máquina de guerra	2026	428436ee-ed1f-4070-b86b-1101a88e7be8
6df16c68-026e-4097-ba69-3d4f5f04abee	2026-04-10 15:41:23.943488+02	1054867	https://image.tmdb.org/t/p/w500/iZ1499F6hYxDxiqioy8oSUaxipG.jpg	Una batalla tras otra	2025	428436ee-ed1f-4070-b86b-1101a88e7be8
46405fc3-4342-4570-ad5f-2ba6d4ced6a0	2026-04-10 15:41:33.740996+02	1100782	https://image.tmdb.org/t/p/w500/aQtWauWpy5KQEHsBURDnoTD6svd.jpg	Smile 2	2024	428436ee-ed1f-4070-b86b-1101a88e7be8
aa270f67-5ccc-4eb2-9ea1-046f34ea5b22	2026-04-10 15:41:47.701887+02	1390394	https://image.tmdb.org/t/p/w500/s1zEsvB5XoS0Gqr7tmnNZExVdRI.jpg	Baila, Vini	2025	428436ee-ed1f-4070-b86b-1101a88e7be8
a7ef74c3-2ee6-4aca-957c-03c64bd11d4e	2026-04-10 15:48:31.545919+02	57214	https://image.tmdb.org/t/p/w500/owF9NIcQOPFAOjp2pni8BsVlnRq.jpg	Proyecto X	2012	428436ee-ed1f-4070-b86b-1101a88e7be8
e68e4b75-0373-4007-81a8-f014067570ae	2026-04-28 08:13:38.008882+02	215092	https://image.tmdb.org/t/p/w500/2TsfGgXutoakJjU1XgheX0M6iBP.jpg	Machos alfa	2022	5f00e8eb-105a-407e-93f0-e7f34061a517
7618dc3c-6e36-4707-9f3c-405c2f5d68ce	2026-04-28 08:50:48.405344+02	875828	https://image.tmdb.org/t/p/w500/l9S8MLUyi2diTc2Gf3KDAOQTuVx.jpg	Peaky Blinders: El hombre inmortal	2026	1be7569b-9f17-4ede-8fde-37782b3ca99d
d91c6e6b-0984-4e08-a279-7870ce934b54	2026-04-28 08:50:54.800468+02	1236153	https://image.tmdb.org/t/p/w500/9zBNg8koM3fjUTPT7QeJHuGG2r9.jpg	Sin piedad	2026	1be7569b-9f17-4ede-8fde-37782b3ca99d
5637fbe8-761b-49ae-8948-e4f315fa6e55	2026-04-28 08:51:01.319148+02	1297842	https://image.tmdb.org/t/p/w500/l8Y8o3RpFg54gmQQ8GVaxVroFmY.jpg	GOAT: Como cabras	2026	1be7569b-9f17-4ede-8fde-37782b3ca99d
bb78a6be-36b4-4ace-9e83-b908b904e389	2026-04-28 08:51:08.88867+02	1265609	https://image.tmdb.org/t/p/w500/5C18uOTbDZRvVIZcVRO747k2fUi.jpg	Máquina de guerra	2026	1be7569b-9f17-4ede-8fde-37782b3ca99d
1002c288-b270-4f7f-a7e7-0215442a524c	2026-04-28 08:51:18.160705+02	1054867	https://image.tmdb.org/t/p/w500/iZ1499F6hYxDxiqioy8oSUaxipG.jpg	Una batalla tras otra	2025	1be7569b-9f17-4ede-8fde-37782b3ca99d
d0b1cc6b-5e9c-406b-b6f6-505f7a7a4aa2	2026-04-28 08:51:27.928255+02	1100782	https://image.tmdb.org/t/p/w500/aQtWauWpy5KQEHsBURDnoTD6svd.jpg	Smile 2	2024	1be7569b-9f17-4ede-8fde-37782b3ca99d
50cd28f2-a249-4363-a6b2-c9d4e402bd28	2026-04-28 08:51:37.377326+02	1390394	https://image.tmdb.org/t/p/w500/s1zEsvB5XoS0Gqr7tmnNZExVdRI.jpg	Baila, Vini	2025	1be7569b-9f17-4ede-8fde-37782b3ca99d
6ee86579-f4e8-41a5-8865-9fae17463171	2026-04-28 08:51:49.200172+02	57214	https://image.tmdb.org/t/p/w500/owF9NIcQOPFAOjp2pni8BsVlnRq.jpg	Proyecto X	2012	1be7569b-9f17-4ede-8fde-37782b3ca99d
60e1e8d8-fa7a-4b19-bbce-3b5d145a5907	2026-04-28 08:51:58.77379+02	62206	https://image.tmdb.org/t/p/w500/bOHbTyE3wDKu2ExhsfhGZQOIVUm.jpg	30 minutos o menos	2011	1be7569b-9f17-4ede-8fde-37782b3ca99d
3bfcfc25-3de8-40a4-8bd7-6ac67c2ce89f	2026-04-28 08:52:05.659108+02	1268127	https://image.tmdb.org/t/p/w500/3xI81SsZHRKY0ltVKbILbvU3wa4.jpg	Humint	2026	1be7569b-9f17-4ede-8fde-37782b3ca99d
736d44ea-e4e7-466b-a521-b1f5f3a19e74	2026-04-28 08:52:23.713361+02	8292	https://image.tmdb.org/t/p/w500/1HtCW7TO9HvsEyK7Nm1bQYwcgxD.jpg	Cuatro hermanos	2005	1be7569b-9f17-4ede-8fde-37782b3ca99d
b6d5276b-65d3-488a-aba7-6a52bbe4979c	2026-04-28 08:52:37.824394+02	18476	https://image.tmdb.org/t/p/w500/lQNVNPpCJzRsTMqDaAVC3RPb8RS.jpg	Los jinetes del Apocalipsis	2009	1be7569b-9f17-4ede-8fde-37782b3ca99d
5ffd5248-2f2a-4f72-a74d-ee69ce2be3b5	2026-04-28 08:52:52.412607+02	809140	https://image.tmdb.org/t/p/w500/hQ7rObRPHpLwyfz0a2wasTAXfSK.jpg	El milagro del padre Stu	2022	1be7569b-9f17-4ede-8fde-37782b3ca99d
f2d2915d-1c29-4964-b78d-69987c440d23	2026-04-28 08:53:02.169254+02	1359	https://image.tmdb.org/t/p/w500/ySPCri7VfAjbLLkMKs7Zjn6Njco.jpg	American Psycho	2000	1be7569b-9f17-4ede-8fde-37782b3ca99d
aec12357-9b46-498a-beda-4721e1438e7e	2026-04-28 08:53:10.838345+02	913290	https://image.tmdb.org/t/p/w500/1YyGqOsEkWjdkBMeUrxhtiLRvlD.jpg	Barbarian	2022	1be7569b-9f17-4ede-8fde-37782b3ca99d
a3861bcb-cc16-45a0-845a-85fca6657652	2026-04-28 08:53:18.738761+02	290250	https://image.tmdb.org/t/p/w500/kgMpKQMKyS7oYmPffKyyOv0VqyT.jpg	Dos buenos tipos	2016	1be7569b-9f17-4ede-8fde-37782b3ca99d
52dd971d-5699-45fd-91c9-93432db77f0c	2026-04-28 08:53:29.749223+02	308266	https://image.tmdb.org/t/p/w500/jwBcQXp7WgYgrcANpnuLCCfLpkK.jpg	Juego de armas	2016	1be7569b-9f17-4ede-8fde-37782b3ca99d
932f0e9e-a9c7-479a-b78a-88717405adfb	2026-04-28 08:54:08.903567+02	76479	https://image.tmdb.org/t/p/w500/5kgY14oisiHcJ4zq0Xgq1e97PHm.jpg	The Boys	2019	ea94a3a7-c0b1-47ce-b52f-37abdef5d322
4f013645-e0d1-4f0c-8f35-fd03584ce319	2026-05-01 10:47:16.329032+02	224263	https://image.tmdb.org/t/p/w500/ssls8OpHHXtHcvIy7AiXipBddQX.jpg	Stranger Things: Relatos del 85	2026	1be7569b-9f17-4ede-8fde-37782b3ca99d
bd2bd0bc-af73-4035-87c0-020b80c101ba	2026-05-01 10:47:29.74196+02	215092	https://image.tmdb.org/t/p/w500/2TsfGgXutoakJjU1XgheX0M6iBP.jpg	Machos alfa	2022	1be7569b-9f17-4ede-8fde-37782b3ca99d
826a9e06-9a23-451b-b64b-00a8324b123f	2026-05-06 17:48:33.359934+02	499932	https://image.tmdb.org/t/p/w500/8i8JpU7gNHFUH9gsGko9NwMJqsZ.jpg	El diablo a todas horas	2020	1be7569b-9f17-4ede-8fde-37782b3ca99d
1bb9a8ec-9abf-4562-a885-bbf768c12cef	2026-05-14 19:47:53.8477+02	95557	https://image.tmdb.org/t/p/w500/AdcfiT5FsjUooyP7CrKzEGmP9K1.jpg	INVENCIBLE	2021	1be7569b-9f17-4ede-8fde-37782b3ca99d
b9ed060c-ef3d-4c47-be39-ebddfe30a2f7	2026-05-14 19:48:17.521702+02	202555	https://image.tmdb.org/t/p/w500/zNH50kOA5Zm5QSFqeIu3hJGuNdO.jpg	Daredevil: Born Again	2025	1be7569b-9f17-4ede-8fde-37782b3ca99d
\.


--
-- TOC entry 5060 (class 0 OID 16600)
-- Dependencies: 220
-- Data for Name: lists; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.lists (id, created_at, name, type, updated_at, user_id) FROM stdin;
428436ee-ed1f-4070-b86b-1101a88e7be8	2026-04-10 13:02:57.492961+02	Visto en 2026	CUSTOM	2026-04-10 13:02:57.492961+02	4beac43a-2c09-4a20-9fdd-f6540b8c8e4d
5f00e8eb-105a-407e-93f0-e7f34061a517	2026-04-26 15:53:48.771411+02	Pendientes	DEFAULT	2026-04-26 15:53:48.771411+02	eb5d95fc-7345-4dde-85c8-0d8bf789a03f
1be7569b-9f17-4ede-8fde-37782b3ca99d	2026-04-28 08:49:40.391416+02	Vistas en 2026	CUSTOM	2026-04-28 08:49:40.391416+02	51df91a9-024b-4206-8053-eb1a8e8b25e7
ea94a3a7-c0b1-47ce-b52f-37abdef5d322	2026-04-28 08:53:58.868832+02	Series en Progreso	CUSTOM	2026-04-28 08:53:58.868832+02	51df91a9-024b-4206-8053-eb1a8e8b25e7
\.


--
-- TOC entry 5061 (class 0 OID 16611)
-- Dependencies: 221
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, avatar_url, blocked, created_at, display_name, email, last_login_at, password_hash, role) FROM stdin;
3aeaa768-171a-4706-b88a-bff2c5b2b982	/uploads/avatars/c0d10a74-a8f2-48a1-a3be-c3d293d702cc-tom-and-jerry-laughing.gif	f	2026-04-30 13:17:34.492029+02	IvanPrueba	ivan2prueba@gmail.com	2026-05-01 11:10:12.84253+02	$2a$10$z2axVRyDzSkV8u0/mjKA7OmTsTJ0kSgVxCCvyK7tO9cQF/Xsu/Nme	USER
cf8baa39-0930-409f-b3dd-d4857c59472e	\N	f	2026-04-28 08:44:02.490559+02	Ivan	ivan2@test.com	\N	$2a$10$jdxQce/PuFFNyMv.Pp/dTeLKaOr8xWyxjlqatpxv2tb6XcgqzAABu	USER
3ed144c4-986f-40de-9e99-219ea3f32a0b	\N	f	2026-04-28 15:34:59.260835+02	Prueba	prueba@gmail.com	\N	$2a$10$vt.qhIuRKYef7nwW83DpjOlycS11AEH5wlfDJdDHpwLD8VcBTFWGi	USER
a9d79d86-6e32-4513-b3b8-659ba07eeb8c	/uploads/avatars/6742e796-53a8-4e3d-8896-c7d9c67cb0e8-200w.gif	f	2026-05-06 17:37:35.645368+02	Ivanin	ivanin@gmail.com	\N	$2a$10$kE1Ixs8Tsx2qC6dsmbjUPO6K3jK0/MZcIrQTwESjW.XSVjgLGscvK	USER
35522083-508f-4186-814d-f4aa24cd7703	\N	f	2026-04-28 15:44:51.052959+02	Prueba2	prueba2@gmail.com	\N	$2a$10$nr6yg90vuI82ZrTpimLM5O93iwro.vzE7N2ciOKh0JCTKzVdT0CA6	USER
eb5d95fc-7345-4dde-85c8-0d8bf789a03f	\N	f	2026-04-26 15:11:45.525635+02	Ivan	ivan@test.com	2026-05-06 16:40:07.921761+02	$2a$10$JDpNNYkxWg6SytDV1Iiu1O3kLefyHX8hJDHcfAxH4SMwlFP28NgBS	USER
2cf05058-bf44-4242-8eec-6ac64e16863d	\N	f	2026-05-14 20:16:08.066398+02	Admin	admin@traiviu.com	2026-05-15 16:55:37.851254+02	$2a$10$iepSM2GHma4iVz6DFCV63OfQkgSDpUdHtLMWzKi79vA/IeUSLzSRG	ADMIN
4beac43a-2c09-4a20-9fdd-f6540b8c8e4d	\N	t	2026-03-25 17:06:01.386763+01	Iván	ivan@example.com	\N	03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4	USER
51df91a9-024b-4206-8053-eb1a8e8b25e7	/uploads/avatars/61e7b563-8025-486e-b2db-1174a4325388-giphy.gif	f	2026-04-28 08:49:23.842043+02	Ivan Iarunichev	iarunichevivan@gmail.com	2026-05-15 17:00:12.48969+02	$2a$10$zqJSA7mEcMGzmgWNXQ/db.KbtnaR2IA7v.4yi00BAEn8bgmz.CAPu	USER
\.


--
-- TOC entry 4902 (class 2606 OID 24909)
-- Name: clan_feed clan_feed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clan_feed
    ADD CONSTRAINT clan_feed_pkey PRIMARY KEY (id);


--
-- TOC entry 4904 (class 2606 OID 24919)
-- Name: clan_members clan_members_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clan_members
    ADD CONSTRAINT clan_members_pkey PRIMARY KEY (clan_id, user_id);


--
-- TOC entry 4906 (class 2606 OID 24931)
-- Name: clan_messages clan_messages_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clan_messages
    ADD CONSTRAINT clan_messages_pkey PRIMARY KEY (id);


--
-- TOC entry 4908 (class 2606 OID 24944)
-- Name: clans clans_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clans
    ADD CONSTRAINT clans_pkey PRIMARY KEY (id);


--
-- TOC entry 4900 (class 2606 OID 16658)
-- Name: list_items list_items_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.list_items
    ADD CONSTRAINT list_items_pkey PRIMARY KEY (id);


--
-- TOC entry 4894 (class 2606 OID 16610)
-- Name: lists lists_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT lists_pkey PRIMARY KEY (id);


--
-- TOC entry 4896 (class 2606 OID 16628)
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4910 (class 2606 OID 24946)
-- Name: clans uknpmk9wr8qexy29hbt6cvnk76f; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clans
    ADD CONSTRAINT uknpmk9wr8qexy29hbt6cvnk76f UNIQUE (invite_code);


--
-- TOC entry 4898 (class 2606 OID 16624)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4912 (class 2606 OID 16659)
-- Name: list_items fk9qo4efm2sp1p91w6x4mlgqh5m; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.list_items
    ADD CONSTRAINT fk9qo4efm2sp1p91w6x4mlgqh5m FOREIGN KEY (list_id) REFERENCES public.lists(id);


--
-- TOC entry 4911 (class 2606 OID 16634)
-- Name: lists fke59kv852m4k3g8kmefph4i3kx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lists
    ADD CONSTRAINT fke59kv852m4k3g8kmefph4i3kx FOREIGN KEY (user_id) REFERENCES public.users(id);


-- Completed on 2026-05-15 18:51:47

--
-- PostgreSQL database dump complete
--