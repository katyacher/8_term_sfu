--
-- PostgreSQL database dump
--

-- Dumped from database version 12.22 (Ubuntu 12.22-0ubuntu0.20.04.2)
-- Dumped by pg_dump version 12.22 (Ubuntu 12.22-0ubuntu0.20.04.2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: sfu
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    phone character varying(20),
    email character varying(100)
);


ALTER TABLE public.customer OWNER TO sfu;

--
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: sfu
--

CREATE SEQUENCE public.customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.customer_id_seq OWNER TO sfu;

--
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sfu
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;


--
-- Name: jewelry; Type: TABLE; Schema: public; Owner: sfu
--

CREATE TABLE public.jewelry (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    price numeric(10,2),
    weight numeric(8,2),
    type_id integer NOT NULL,
    manufacturer character varying(100) NOT NULL,
    CONSTRAINT jewelry_price_check CHECK ((price > (0)::numeric)),
    CONSTRAINT jewelry_weight_check CHECK ((weight > (0)::numeric))
);


ALTER TABLE public.jewelry OWNER TO sfu;

--
-- Name: jewelry_id_seq; Type: SEQUENCE; Schema: public; Owner: sfu
--

CREATE SEQUENCE public.jewelry_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.jewelry_id_seq OWNER TO sfu;

--
-- Name: jewelry_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sfu
--

ALTER SEQUENCE public.jewelry_id_seq OWNED BY public.jewelry.id;


--
-- Name: jewelrymaterial; Type: TABLE; Schema: public; Owner: sfu
--

CREATE TABLE public.jewelrymaterial (
    jewelry_id integer NOT NULL,
    material_id integer NOT NULL
);


ALTER TABLE public.jewelrymaterial OWNER TO sfu;

--
-- Name: jewelrytype; Type: TABLE; Schema: public; Owner: sfu
--

CREATE TABLE public.jewelrytype (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.jewelrytype OWNER TO sfu;

--
-- Name: jewelrytype_id_seq; Type: SEQUENCE; Schema: public; Owner: sfu
--

CREATE SEQUENCE public.jewelrytype_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.jewelrytype_id_seq OWNER TO sfu;

--
-- Name: jewelrytype_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sfu
--

ALTER SEQUENCE public.jewelrytype_id_seq OWNED BY public.jewelrytype.id;


--
-- Name: material; Type: TABLE; Schema: public; Owner: sfu
--

CREATE TABLE public.material (
    id integer NOT NULL,
    type character varying(20) NOT NULL,
    name character varying(100) NOT NULL,
    carat numeric(5,2),
    quality character varying(50),
    CONSTRAINT material_type_check CHECK (((type)::text = ANY ((ARRAY['metal'::character varying, 'gemstone'::character varying])::text[])))
);


ALTER TABLE public.material OWNER TO sfu;

--
-- Name: material_id_seq; Type: SEQUENCE; Schema: public; Owner: sfu
--

CREATE SEQUENCE public.material_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.material_id_seq OWNER TO sfu;

--
-- Name: material_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sfu
--

ALTER SEQUENCE public.material_id_seq OWNED BY public.material.id;


--
-- Name: orderitem; Type: TABLE; Schema: public; Owner: sfu
--

CREATE TABLE public.orderitem (
    order_id integer NOT NULL,
    jewelry_id integer NOT NULL,
    quantity integer NOT NULL,
    price numeric(10,2) NOT NULL,
    CONSTRAINT orderitem_price_check CHECK ((price >= (0)::numeric)),
    CONSTRAINT orderitem_quantity_check CHECK ((quantity > 0))
);


ALTER TABLE public.orderitem OWNER TO sfu;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: sfu
--

CREATE TABLE public.orders (
    id integer NOT NULL,
    order_date date DEFAULT CURRENT_DATE NOT NULL,
    total numeric(10,2),
    customer_id integer NOT NULL,
    CONSTRAINT orders_total_check CHECK ((total >= (0)::numeric))
);


ALTER TABLE public.orders OWNER TO sfu;

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: sfu
--

CREATE SEQUENCE public.orders_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_id_seq OWNER TO sfu;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: sfu
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- Name: jewelry id; Type: DEFAULT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelry ALTER COLUMN id SET DEFAULT nextval('public.jewelry_id_seq'::regclass);


--
-- Name: jewelrytype id; Type: DEFAULT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelrytype ALTER COLUMN id SET DEFAULT nextval('public.jewelrytype_id_seq'::regclass);


--
-- Name: material id; Type: DEFAULT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.material ALTER COLUMN id SET DEFAULT nextval('public.material_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: sfu
--

COPY public.customer (id, name, phone, email) FROM stdin;
1	Иванова Анна	+79161234567	anna@mail.ru
2	Петров Дмитрий	+79035556677	dmitry@yandex.ru
3	Сидорова Мария	+79269874563	maria@gmail.com
4	Козлов Артем	+79031237894	artem@mail.com
5	Новикова Елена	+79150983456	elena@yahoo.com
6	Васильев Игорь	+79087651234	igor@bk.ru
7	Морозова Ольга	+79265439876	olga@inbox.ru
8	Лебедев Павел	+79024561239	pavel@list.ru
9	Смирнова Виктория	+79167894523	vika@rambler.ru
10	Кузнецов Андрей	+79035671234	andrey@gmail.com
\.


--
-- Data for Name: jewelry; Type: TABLE DATA; Schema: public; Owner: sfu
--

COPY public.jewelry (id, name, price, weight, type_id, manufacturer) FROM stdin;
1	Кольцо обручальное	1500.00	5.00	1	Tiffany
2	Серьги-гвоздики	450.00	3.20	2	Sokolov
3	Цепочка золотая	1200.00	8.50	3	Adamas
4	Браслет Pandora	950.00	12.00	4	Pandora
5	Подвеска-сердце	300.00	2.10	5	Swarovski
6	Кулон с бриллиантом	2500.00	4.80	6	Cartier
7	Пирсинг в нос	150.00	1.50	7	Local Jewelry
8	Запонки серебряные	200.00	6.00	8	Frey Wille
9	Брошь Vintage	1800.00	7.30	9	Chopard
10	Диадема свадебная	5000.00	15.00	10	Harry Winston
\.


--
-- Data for Name: jewelrymaterial; Type: TABLE DATA; Schema: public; Owner: sfu
--

COPY public.jewelrymaterial (jewelry_id, material_id) FROM stdin;
1	1
1	3
2	2
3	1
4	4
5	5
6	3
7	7
8	2
9	9
\.


--
-- Data for Name: jewelrytype; Type: TABLE DATA; Schema: public; Owner: sfu
--

COPY public.jewelrytype (id, name) FROM stdin;
1	Кольцо
2	Серьги
3	Цепочка
4	Браслет
5	Подвеска
6	Кулон
7	Пирсинг
8	Запонки
9	Брошь
10	Диадема
\.


--
-- Data for Name: material; Type: TABLE DATA; Schema: public; Owner: sfu
--

COPY public.material (id, type, name, carat, quality) FROM stdin;
1	metal	Золото 585	\N	Высшая проба
2	metal	Серебро 925	\N	Стандарт
3	gemstone	Бриллиант	1.20	VVS1
4	metal	Платина	\N	950 проба
5	gemstone	Сапфир	0.80	Синий
6	gemstone	Изумруд	0.50	AAA
7	metal	Титан	\N	Медицинский
8	gemstone	Рубин	0.70	Кровавый
9	metal	Палладий	\N	999 проба
10	gemstone	Аметист	0.30	Фиолетовый
\.


--
-- Data for Name: orderitem; Type: TABLE DATA; Schema: public; Owner: sfu
--

COPY public.orderitem (order_id, jewelry_id, quantity, price) FROM stdin;
1	1	1	1500.00
2	2	1	450.00
3	3	1	1200.00
4	4	1	950.00
5	5	1	300.00
6	6	1	2500.00
7	7	1	150.00
8	8	1	200.00
9	9	1	1800.00
10	10	1	5000.00
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: sfu
--

COPY public.orders (id, order_date, total, customer_id) FROM stdin;
1	2023-10-01	1500.00	1
2	2023-10-02	450.00	2
3	2023-10-03	1200.00	3
4	2023-10-04	950.00	4
5	2023-10-05	300.00	5
6	2023-10-06	2500.00	6
7	2023-10-07	150.00	7
8	2023-10-08	200.00	8
9	2023-10-09	1800.00	9
10	2023-10-10	5000.00	10
\.


--
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sfu
--

SELECT pg_catalog.setval('public.customer_id_seq', 10, true);


--
-- Name: jewelry_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sfu
--

SELECT pg_catalog.setval('public.jewelry_id_seq', 10, true);


--
-- Name: jewelrytype_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sfu
--

SELECT pg_catalog.setval('public.jewelrytype_id_seq', 10, true);


--
-- Name: material_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sfu
--

SELECT pg_catalog.setval('public.material_id_seq', 10, true);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: sfu
--

SELECT pg_catalog.setval('public.orders_id_seq', 10, true);


--
-- Name: customer customer_email_key; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_email_key UNIQUE (email);


--
-- Name: customer customer_phone_key; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_phone_key UNIQUE (phone);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- Name: jewelry jewelry_pkey; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelry
    ADD CONSTRAINT jewelry_pkey PRIMARY KEY (id);


--
-- Name: jewelrymaterial jewelrymaterial_pkey; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelrymaterial
    ADD CONSTRAINT jewelrymaterial_pkey PRIMARY KEY (jewelry_id, material_id);


--
-- Name: jewelrytype jewelrytype_name_key; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelrytype
    ADD CONSTRAINT jewelrytype_name_key UNIQUE (name);


--
-- Name: jewelrytype jewelrytype_pkey; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelrytype
    ADD CONSTRAINT jewelrytype_pkey PRIMARY KEY (id);


--
-- Name: material material_pkey; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.material
    ADD CONSTRAINT material_pkey PRIMARY KEY (id);


--
-- Name: orderitem orderitem_pkey; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.orderitem
    ADD CONSTRAINT orderitem_pkey PRIMARY KEY (order_id, jewelry_id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: jewelry jewelry_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelry
    ADD CONSTRAINT jewelry_type_id_fkey FOREIGN KEY (type_id) REFERENCES public.jewelrytype(id);


--
-- Name: jewelrymaterial jewelrymaterial_jewelry_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelrymaterial
    ADD CONSTRAINT jewelrymaterial_jewelry_id_fkey FOREIGN KEY (jewelry_id) REFERENCES public.jewelry(id);


--
-- Name: jewelrymaterial jewelrymaterial_material_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.jewelrymaterial
    ADD CONSTRAINT jewelrymaterial_material_id_fkey FOREIGN KEY (material_id) REFERENCES public.material(id);


--
-- Name: orderitem orderitem_jewelry_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.orderitem
    ADD CONSTRAINT orderitem_jewelry_id_fkey FOREIGN KEY (jewelry_id) REFERENCES public.jewelry(id);


--
-- Name: orderitem orderitem_order_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.orderitem
    ADD CONSTRAINT orderitem_order_id_fkey FOREIGN KEY (order_id) REFERENCES public.orders(id);


--
-- Name: orders orders_customer_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: sfu
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES public.customer(id);


--
-- PostgreSQL database dump complete
--

