--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4
-- Dumped by pg_dump version 12.4

-- Started on 2026-04-18 15:10:46

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
-- TOC entry 202 (class 1259 OID 37729)
-- Name: autores; Type: TABLE; Schema: public; Owner: Alejandro
--

CREATE TABLE public.autores (
    id integer NOT NULL,
    nombre character varying(50),
    apellido character varying(50)
);


ALTER TABLE public.autores OWNER TO "Alejandro";

--
-- TOC entry 204 (class 1259 OID 37739)
-- Name: libros; Type: TABLE; Schema: public; Owner: Alejandro
--

CREATE TABLE public.libros (
    id integer NOT NULL,
    nombre character varying(100),
    id_autor integer
);


ALTER TABLE public.libros OWNER TO "Alejandro";

--
-- TOC entry 205 (class 1259 OID 37749)
-- Name: prestamos; Type: TABLE; Schema: public; Owner: Alejandro
--

CREATE TABLE public.prestamos (
    id integer NOT NULL,
    id_usuario integer,
    id_libro integer,
    f_prestamo date,
    f_devolucion date
);


ALTER TABLE public.prestamos OWNER TO "Alejandro";

--
-- TOC entry 203 (class 1259 OID 37734)
-- Name: usuarios; Type: TABLE; Schema: public; Owner: Alejandro
--

CREATE TABLE public.usuarios (
    id integer NOT NULL,
    nombre character varying(50),
    apellido character varying(50)
);


ALTER TABLE public.usuarios OWNER TO "Alejandro";

--
-- TOC entry 2834 (class 0 OID 37729)
-- Dependencies: 202
-- Data for Name: autores; Type: TABLE DATA; Schema: public; Owner: Alejandro
--

COPY public.autores (id, nombre, apellido) FROM stdin;
101	Brandon	Sanderson
102	Robert	Jordan
103	Andrej	Sapkowski
104	Joe	Abercrombie
105	George	R.R. Martin
106	Steven	Erikson
\.


--
-- TOC entry 2836 (class 0 OID 37739)
-- Dependencies: 204
-- Data for Name: libros; Type: TABLE DATA; Schema: public; Owner: Alejandro
--

COPY public.libros (id, nombre, id_autor) FROM stdin;
1	El camino de los reyes	101
2	Los diablos	104
3	Festín de cuervos	105
4	Encrucijada en el crepúsculo	102
5	Elantris	101
6	Los jardines de la Luna	106
\.


--
-- TOC entry 2837 (class 0 OID 37749)
-- Dependencies: 205
-- Data for Name: prestamos; Type: TABLE DATA; Schema: public; Owner: Alejandro
--

COPY public.prestamos (id, id_usuario, id_libro, f_prestamo, f_devolucion) FROM stdin;
1001	13	3	2026-04-17	2026-05-02
1002	15	4	2026-04-13	2026-04-28
1003	14	1	2026-04-12	2026-04-27
1004	14	5	2026-04-18	2026-05-03
1005	11	2	2026-04-15	2026-04-30
1006	12	6	2026-04-18	2026-05-03
\.


--
-- TOC entry 2835 (class 0 OID 37734)
-- Dependencies: 203
-- Data for Name: usuarios; Type: TABLE DATA; Schema: public; Owner: Alejandro
--

COPY public.usuarios (id, nombre, apellido) FROM stdin;
11	Juan	Fernandez
12	Maria	Rodriguez
13	Jaime	Rivero
14	Luisa	Perez
15	Marta	Cruz
16	Roberto	Ruiz
\.


--
-- TOC entry 2698 (class 2606 OID 37733)
-- Name: autores autores_pkey; Type: CONSTRAINT; Schema: public; Owner: Alejandro
--

ALTER TABLE ONLY public.autores
    ADD CONSTRAINT autores_pkey PRIMARY KEY (id);


--
-- TOC entry 2702 (class 2606 OID 37743)
-- Name: libros libros_pkey; Type: CONSTRAINT; Schema: public; Owner: Alejandro
--

ALTER TABLE ONLY public.libros
    ADD CONSTRAINT libros_pkey PRIMARY KEY (id);


--
-- TOC entry 2704 (class 2606 OID 37753)
-- Name: prestamos prestamos_pkey; Type: CONSTRAINT; Schema: public; Owner: Alejandro
--

ALTER TABLE ONLY public.prestamos
    ADD CONSTRAINT prestamos_pkey PRIMARY KEY (id);


--
-- TOC entry 2700 (class 2606 OID 37738)
-- Name: usuarios usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: Alejandro
--

ALTER TABLE ONLY public.usuarios
    ADD CONSTRAINT usuarios_pkey PRIMARY KEY (id);


--
-- TOC entry 2705 (class 2606 OID 37744)
-- Name: libros libros_id_autor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Alejandro
--

ALTER TABLE ONLY public.libros
    ADD CONSTRAINT libros_id_autor_fkey FOREIGN KEY (id_autor) REFERENCES public.autores(id);


--
-- TOC entry 2707 (class 2606 OID 37759)
-- Name: prestamos prestamos_id_libro_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Alejandro
--

ALTER TABLE ONLY public.prestamos
    ADD CONSTRAINT prestamos_id_libro_fkey FOREIGN KEY (id_libro) REFERENCES public.libros(id);


--
-- TOC entry 2706 (class 2606 OID 37754)
-- Name: prestamos prestamos_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Alejandro
--

ALTER TABLE ONLY public.prestamos
    ADD CONSTRAINT prestamos_id_usuario_fkey FOREIGN KEY (id_usuario) REFERENCES public.usuarios(id);


-- Completed on 2026-04-18 15:10:46

--
-- PostgreSQL database dump complete
--

