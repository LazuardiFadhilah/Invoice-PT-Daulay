-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 26 Apr 2021 pada 18.03
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kkp`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `angkut_limbah`
--

CREATE TABLE `angkut_limbah` (
  `id_angkut` varchar(25) NOT NULL,
  `id_mobil` varchar(25) NOT NULL,
  `id_user` varchar(25) NOT NULL,
  `id_inv` varchar(25) NOT NULL,
  `id_customer` varchar(25) NOT NULL,
  `tanggal_angkut` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `angkut_limbah`
--

INSERT INTO `angkut_limbah` (`id_angkut`, `id_mobil`, `id_user`, `id_inv`, `id_customer`, `tanggal_angkut`) VALUES
('AGT0001', 'MBL0001', 'U0002', 'INV0001', 'UC0002', '2021-04-19'),
('AGT0002', 'MBL0002', 'U0002', 'INV0002', 'UC0003', '2021-04-09'),
('AGT0003', 'MBL0002', 'U0002', 'INV0002', 'UC0003', '2021-04-30');

-- --------------------------------------------------------

--
-- Struktur dari tabel `customer`
--

CREATE TABLE `customer` (
  `id_customer` varchar(25) NOT NULL,
  `nama_customer` varchar(100) NOT NULL,
  `alamat_customer` text NOT NULL,
  `no_telp_customer` varchar(12) NOT NULL,
  `npwp` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `customer`
--

INSERT INTO `customer` (`id_customer`, `nama_customer`, `alamat_customer`, `no_telp_customer`, `npwp`) VALUES
('UC0001', 'Lazuardi Fadhilah', 'Otista Cawang 1 RT 05 RW 12 No 18A', '081584924335', '213546548987526'),
('UC0002', 'Wahyu Ari Wibowo', 'Bogor Kota', '081584924336', '02154878'),
('UC0003', 'Cahyadi Dwi Lestiyanto', 'Bogor Desa', '15424654654', '54868743521'),
('UC0004', 'Diva Kiko Augusta', 'Depok', '0815481866', '2145631654');

-- --------------------------------------------------------

--
-- Struktur dari tabel `invoice`
--

CREATE TABLE `invoice` (
  `id_inv` varchar(25) NOT NULL,
  `jenis_limbah` varchar(50) NOT NULL,
  `berat_total` int(10) NOT NULL,
  `id_customer` varchar(25) NOT NULL,
  `total_harga` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `invoice`
--

INSERT INTO `invoice` (`id_inv`, `jenis_limbah`, `berat_total`, `id_customer`, `total_harga`) VALUES
('INV0001', 'Limbah Cair', 15, 'UC0002', '225000'),
('INV0002', 'Limbah Padat', 30, 'UC0003', '300000'),
('INV0003', 'Limbah Cair', 20, 'UC0002', '300000');

-- --------------------------------------------------------

--
-- Struktur dari tabel `mobil`
--

CREATE TABLE `mobil` (
  `id_mobil` varchar(25) NOT NULL,
  `jenis_mobil` varchar(50) NOT NULL,
  `no_polis` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `mobil`
--

INSERT INTO `mobil` (`id_mobil`, `jenis_mobil`, `no_polis`) VALUES
('MBL0001', 'Dump Truck', 'B 1456 TER'),
('MBL0002', 'Dump Truck', 'B 2012 TYE'),
('MBL0003', 'Armroll Truck', 'B 1448 CAE');

-- --------------------------------------------------------

--
-- Struktur dari tabel `project`
--

CREATE TABLE `project` (
  `id_project` varchar(25) NOT NULL,
  `id_inv` varchar(25) NOT NULL,
  `total_harga` int(100) NOT NULL,
  `total_reimburse` int(100) NOT NULL,
  `total_untung` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `project`
--

INSERT INTO `project` (`id_project`, `id_inv`, `total_harga`, `total_reimburse`, `total_untung`) VALUES
('PRJ0001', 'INV0001', 500000, 200000, 300000),
('PRJ0002', 'INV0002', 300000, 50000, 250000),
('PRJ0003', 'INV0003', 300000, 0, 300000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `reimburse`
--

CREATE TABLE `reimburse` (
  `id_reimburse` varchar(25) NOT NULL,
  `id_inv` varchar(25) NOT NULL,
  `kegiatan` varchar(100) NOT NULL,
  `biaya` int(12) NOT NULL,
  `id_user` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `reimburse`
--

INSERT INTO `reimburse` (`id_reimburse`, `id_inv`, `kegiatan`, `biaya`, `id_user`) VALUES
('RMB0001', 'INV0001', 'Pergi antar dokumen ke customer', 20000, 'U0001'),
('RMB0002', 'INV0002', 'Pembelian bensin mobil angkut', 50000, 'U0001');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` varchar(25) NOT NULL,
  `nama_user` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) CHARACTER SET latin1 COLLATE latin1_general_cs NOT NULL,
  `no_telp` varchar(12) NOT NULL,
  `jabatan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id_user`, `nama_user`, `username`, `password`, `no_telp`, `jabatan`) VALUES
('U0001', 'Lazuardi Fadhilah', 'LazuardiFadhilah', '123lazuardi', '081584924335', 'Admin'),
('U0002', 'Fidelia Rahayu Kusuma', 'Fidel', 'Fidedl', '081294839218', 'Petugas'),
('U0003', 'Ardi', 'Ardi', 'Ardi', '1010101010', 'Petugas');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `angkut_limbah`
--
ALTER TABLE `angkut_limbah`
  ADD PRIMARY KEY (`id_angkut`);

--
-- Indeks untuk tabel `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id_customer`);

--
-- Indeks untuk tabel `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`id_inv`);

--
-- Indeks untuk tabel `mobil`
--
ALTER TABLE `mobil`
  ADD PRIMARY KEY (`id_mobil`);

--
-- Indeks untuk tabel `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id_project`);

--
-- Indeks untuk tabel `reimburse`
--
ALTER TABLE `reimburse`
  ADD PRIMARY KEY (`id_reimburse`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
