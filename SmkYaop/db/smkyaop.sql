-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 27 Agu 2022 pada 05.20
-- Versi server: 10.4.24-MariaDB
-- Versi PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `smkyaop`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `absensi`
--

CREATE TABLE `absensi` (
  `idAbsensi` int(11) NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `jamMasuk` time DEFAULT NULL,
  `jamPulang` time DEFAULT NULL,
  `status` int(1) NOT NULL,
  `keterangan` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `absensi`
--

INSERT INTO `absensi` (`idAbsensi`, `idpegawai`, `tanggal`, `jamMasuk`, `jamPulang`, `status`, `keterangan`) VALUES
(5, 1, '2022-08-22', '08:00:00', '10:30:00', 1, 'Masuk'),
(6, 1, '2022-08-24', '09:00:00', '10:30:00', 1, 'Masuk'),
(7, 4, '2022-08-23', '07:30:00', '14:00:00', 1, 'Masuk'),
(8, 4, '2022-08-25', '08:00:00', '13:00:00', 1, 'Masuk');

-- --------------------------------------------------------

--
-- Struktur dari tabel `bpjspegawai`
--

CREATE TABLE `bpjspegawai` (
  `idbpjspegawai` varchar(10) NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `keterangan` varchar(200) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `tgldaftar` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `bpjspegawai`
--

INSERT INTO `bpjspegawai` (`idbpjspegawai`, `idpegawai`, `keterangan`, `jumlah`, `tgldaftar`) VALUES
('BP0001', 3, 'BPJS Kseshatan', 211448, '2022-08-26');

-- --------------------------------------------------------

--
-- Struktur dari tabel `detailnon`
--

CREATE TABLE `detailnon` (
  `idgajian` varchar(10) NOT NULL,
  `gapok` int(11) NOT NULL,
  `jabatan` int(11) NOT NULL,
  `jmlTransport` int(11) NOT NULL,
  `transport` int(11) NOT NULL,
  `pendidikan` int(11) NOT NULL,
  `bpjs` int(11) NOT NULL,
  `tanggalGajian` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `detailtendik`
--

CREATE TABLE `detailtendik` (
  `idgajian` varchar(10) NOT NULL,
  `sksreg` int(11) NOT NULL,
  `totalsksreg` int(11) NOT NULL,
  `sksindustri` int(11) NOT NULL,
  `totalsksindustri` int(11) NOT NULL,
  `jabatan` int(11) NOT NULL,
  `walas` int(11) NOT NULL,
  `jmlTran` int(11) NOT NULL,
  `transport` int(11) NOT NULL,
  `jmlPiket` int(11) NOT NULL,
  `piket` int(11) NOT NULL,
  `masakerja` int(11) NOT NULL,
  `bpjskesehatan` int(11) NOT NULL,
  `tanggalGajian` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `detailtendik`
--

INSERT INTO `detailtendik` (`idgajian`, `sksreg`, `totalsksreg`, `sksindustri`, `totalsksindustri`, `jabatan`, `walas`, `jmlTran`, `transport`, `jmlPiket`, `piket`, `masakerja`, `bpjskesehatan`, `tanggalGajian`) VALUES
('PTD0001', 3, 75000, 0, 0, 3000000, 0, 4, 100000, 0, 0, 1500000, 0, '2022-08-27 01:32:49');

-- --------------------------------------------------------

--
-- Struktur dari tabel `gajiannon`
--

CREATE TABLE `gajiannon` (
  `idgajian` varchar(10) NOT NULL,
  `tanggalAwal` date NOT NULL,
  `tanggalAkhir` date NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `totalgaji` int(11) NOT NULL,
  `totalpotongan` int(11) NOT NULL,
  `dibayarkan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `gajiantendik`
--

CREATE TABLE `gajiantendik` (
  `idgajian` varchar(10) NOT NULL,
  `tanggalAwal` date NOT NULL,
  `tanggalAkhir` date NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `totalgaji` int(11) NOT NULL,
  `totalpotongan` int(11) NOT NULL,
  `dibayarkan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `gajiantendik`
--

INSERT INTO `gajiantendik` (`idgajian`, `tanggalAwal`, `tanggalAkhir`, `idpegawai`, `totalgaji`, `totalpotongan`, `dibayarkan`) VALUES
('PTD0001', '2022-07-27', '2022-08-26', 4, 4675000, 0, 4675000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `jabatan`
--

CREATE TABLE `jabatan` (
  `idjabatan` int(11) NOT NULL,
  `namajabatan` varchar(100) NOT NULL,
  `jenisjabatan` varchar(100) NOT NULL,
  `tunjanganJabatan` int(11) NOT NULL,
  `gapok` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `jabatan`
--

INSERT INTO `jabatan` (`idjabatan`, `namajabatan`, `jenisjabatan`, `tunjanganJabatan`, `gapok`) VALUES
(1, 'Kepala SEkolah', 'Tendik', 3000000, 0),
(3, 'Guru', 'Tendik', 0, 0),
(4, 'Kepala TU', 'Non-Tendik', 2000000, 1000000),
(5, 'Staff TU', 'Non-Tendik', 500000, 1000000),
(6, 'Waka Kurikulum', 'Tendik', 1500000, 0),
(7, 'Waka Sarana Prasarana', 'Tendik', 1500000, 0),
(8, 'Waka Hubin', 'Tendik', 1500000, 0),
(9, 'BP/BK', 'Tendik', 750000, 0);

-- --------------------------------------------------------

--
-- Struktur dari tabel `mengajar`
--

CREATE TABLE `mengajar` (
  `idmengajar` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `jenis` varchar(20) NOT NULL,
  `jammulai` time NOT NULL,
  `jamselesai` time NOT NULL,
  `sks` int(11) NOT NULL,
  `totalharian` int(11) NOT NULL,
  `keterangan` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `mengajar`
--

INSERT INTO `mengajar` (`idmengajar`, `tanggal`, `idpegawai`, `jenis`, `jammulai`, `jamselesai`, `sks`, `totalharian`, `keterangan`) VALUES
('MJ0001', '2022-08-22', 1, 'Industri', '08:00:00', '10:00:00', 2, 60000, 'masuk - kelas XI TKj'),
('MJ0002', '2022-08-24', 1, 'Reguler', '09:00:00', '10:00:00', 1, 25000, 'Masuk - KElas X BDP'),
('MJ0003', '2022-08-23', 4, 'Reguler', '09:00:00', '11:00:00', 2, 50000, 'masuk - KElas XII TKJ'),
('MJ0004', '2022-08-25', 4, 'Reguler', '08:00:00', '09:30:00', 1, 25000, 'masuk - Kela X BDP');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pegawai`
--

CREATE TABLE `pegawai` (
  `idpegawai` int(11) NOT NULL,
  `idjabatan` int(11) NOT NULL,
  `nip` varchar(100) NOT NULL,
  `namapegawai` varchar(200) NOT NULL,
  `jeniskelamin` varchar(15) NOT NULL,
  `tempatlahir` varchar(100) NOT NULL,
  `tgllahir` varchar(100) NOT NULL,
  `alamat` text NOT NULL,
  `telp` varchar(15) NOT NULL,
  `pernikahan` varchar(100) NOT NULL,
  `walas` tinyint(1) NOT NULL,
  `gty` tinyint(1) NOT NULL DEFAULT 0,
  `pendidikan` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `pegawai`
--

INSERT INTO `pegawai` (`idpegawai`, `idjabatan`, `nip`, `namapegawai`, `jeniskelamin`, `tempatlahir`, `tgllahir`, `alamat`, `telp`, `pernikahan`, `walas`, `gty`, `pendidikan`) VALUES
(1, 3, '12345123', 'Muhammad Ali ', 'Laki - Laki', 'Depok', '1997-08-11', 'Jalan jalan kemana ya aa', '0896', 'Lajang', 0, 0, 'S1'),
(3, 4, '12345', 'Ali Indea', 'Laki - Laki', 'Depok', '2022-08-10', 'jalan panjang menuju langit biru', '021', 'Lajang', 0, 0, 'SMA/K'),
(4, 1, '1234567', 'HJ. Akhernawaty, M.Pd', 'Perempuan', 'Jakarta', '1968-08-28', 'jalan jalan', '0219876789', 'Menikah', 0, 1, 'S2');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pegawaijabat`
--

CREATE TABLE `pegawaijabat` (
  `id` int(11) NOT NULL,
  `mulai` date NOT NULL,
  `keluar` date DEFAULT NULL,
  `idPegawai` int(11) NOT NULL,
  `masajabat` int(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `pegawaijabat`
--

INSERT INTO `pegawaijabat` (`id`, `mulai`, `keluar`, `idPegawai`, `masajabat`) VALUES
(2, '2020-08-31', NULL, 1, 1),
(3, '1992-08-02', NULL, 4, 30),
(6, '2017-08-14', NULL, 3, 5);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penggajiannon`
--

CREATE TABLE `penggajiannon` (
  `id` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `gapok` int(11) NOT NULL,
  `jabatan` int(11) NOT NULL,
  `transport` int(11) NOT NULL,
  `masakerja` int(11) NOT NULL,
  `pendidikan` int(11) NOT NULL,
  `bpjs` int(11) NOT NULL,
  `tambahan` int(11) NOT NULL,
  `totalgaji` int(11) NOT NULL,
  `totalpotongan` int(11) NOT NULL,
  `dibayarkan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penggajiannon`
--

INSERT INTO `penggajiannon` (`id`, `tanggal`, `idpegawai`, `gapok`, `jabatan`, `transport`, `masakerja`, `pendidikan`, `bpjs`, `tambahan`, `totalgaji`, `totalpotongan`, `dibayarkan`) VALUES
(2, '2022-08-19', 3, 500000, 2000000, 120000, 0, 0, 0, 0, 2620000, 0, 2620000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penggajiantendik`
--

CREATE TABLE `penggajiantendik` (
  `id` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `sksmk` int(11) NOT NULL,
  `bpjs` int(11) NOT NULL,
  `jabatan` int(11) NOT NULL,
  `masakerja` int(11) NOT NULL,
  `walas` int(11) NOT NULL,
  `transport` int(11) NOT NULL,
  `piket` int(11) NOT NULL,
  `totalgaji` int(11) NOT NULL,
  `totalpotongan` int(11) NOT NULL,
  `dibayarkan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penggajiantendik`
--

INSERT INTO `penggajiantendik` (`id`, `tanggal`, `idpegawai`, `sksmk`, `bpjs`, `jabatan`, `masakerja`, `walas`, `transport`, `piket`, `totalgaji`, `totalpotongan`, `dibayarkan`) VALUES
(1, '2022-08-19', 1, 500000, 0, 300000, 0, 250000, 120000, 0, 1170000, 200000, 970000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `piket`
--

CREATE TABLE `piket` (
  `idpiket` varchar(10) NOT NULL,
  `tanggal` date NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `tambahan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `potongan`
--

CREATE TABLE `potongan` (
  `id` int(11) NOT NULL,
  `namaPotongan` varchar(100) NOT NULL,
  `nominal` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `potongan`
--

INSERT INTO `potongan` (`id`, `namaPotongan`, `nominal`) VALUES
(1, 'Qurban', 100000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `potonganpegawai`
--

CREATE TABLE `potonganpegawai` (
  `id` int(11) NOT NULL,
  `idpegawai` int(11) NOT NULL,
  `tanggal` date NOT NULL,
  `idpotongan` int(11) NOT NULL,
  `keterangan` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `potonganpegawai`
--

INSERT INTO `potonganpegawai` (`id`, `idpegawai`, `tanggal`, `idpotongan`, `keterangan`) VALUES
(1, 1, '2022-08-18', 1, '----');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `iduser` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `akses` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`iduser`, `username`, `password`, `akses`) VALUES
(2, 'kepala', '123456', 'Kepala TU'),
(3, 'admin', '12345', 'Staff TU');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`idAbsensi`);

--
-- Indeks untuk tabel `bpjspegawai`
--
ALTER TABLE `bpjspegawai`
  ADD PRIMARY KEY (`idbpjspegawai`);

--
-- Indeks untuk tabel `gajiannon`
--
ALTER TABLE `gajiannon`
  ADD PRIMARY KEY (`idgajian`);

--
-- Indeks untuk tabel `gajiantendik`
--
ALTER TABLE `gajiantendik`
  ADD PRIMARY KEY (`idgajian`);

--
-- Indeks untuk tabel `jabatan`
--
ALTER TABLE `jabatan`
  ADD PRIMARY KEY (`idjabatan`);

--
-- Indeks untuk tabel `mengajar`
--
ALTER TABLE `mengajar`
  ADD PRIMARY KEY (`idmengajar`);

--
-- Indeks untuk tabel `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`idpegawai`);

--
-- Indeks untuk tabel `pegawaijabat`
--
ALTER TABLE `pegawaijabat`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `penggajiannon`
--
ALTER TABLE `penggajiannon`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `penggajiantendik`
--
ALTER TABLE `penggajiantendik`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `piket`
--
ALTER TABLE `piket`
  ADD PRIMARY KEY (`idpiket`);

--
-- Indeks untuk tabel `potongan`
--
ALTER TABLE `potongan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `potonganpegawai`
--
ALTER TABLE `potonganpegawai`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`iduser`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `absensi`
--
ALTER TABLE `absensi`
  MODIFY `idAbsensi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `jabatan`
--
ALTER TABLE `jabatan`
  MODIFY `idjabatan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT untuk tabel `pegawai`
--
ALTER TABLE `pegawai`
  MODIFY `idpegawai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `pegawaijabat`
--
ALTER TABLE `pegawaijabat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `penggajiannon`
--
ALTER TABLE `penggajiannon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `penggajiantendik`
--
ALTER TABLE `penggajiantendik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `potongan`
--
ALTER TABLE `potongan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `potonganpegawai`
--
ALTER TABLE `potonganpegawai`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `iduser` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
