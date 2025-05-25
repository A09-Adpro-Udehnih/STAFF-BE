package com.example.staffbe.repository;

import com.example.staffbe.model.Payment;
import com.example.staffbe.enums.PaymentMethod;
import com.example.staffbe.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.List;
import java.util.UUID; // Pastikan UUID diimpor
import java.time.LocalDateTime; // Impor LocalDateTime jika belum ada

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        // Hapus semua data sebelum setiap tes untuk memastikan konsistensi
        // paymentRepository.deleteAll(); // Opsional, tergantung kebutuhan isolasi tes

        UUID userIdAsUUID = UUID.randomUUID(); // Buat UUID untuk userId

        testPayment = Payment.builder()
                .userId(userIdAsUUID) // <-- UBAH DI SINI: Gunakan UUID
                .amount(100.0)
                .method(PaymentMethod.BANK_TRANSFER)
                .status(PaymentStatus.PENDING)
                .paymentReference("ref-123")
                .createdAt(java.time.LocalDateTime.now())
                .build();

        // Simpan testPayment yang sudah menggunakan UUID untuk userId
        // Pastikan ID di-generate oleh database atau di-set jika tidak auto-generate
        testPayment = paymentRepository.save(testPayment);
    }

    @Test
    void testFindById() {
        // Cari pembayaran berdasarkan ID
        Optional<Payment> retrievedPayment = paymentRepository.findById(testPayment.getId());

        assertTrue(retrievedPayment.isPresent(), "Pembayaran seharusnya ditemukan");
        assertEquals(testPayment.getId(), retrievedPayment.get().getId());
        assertEquals(testPayment.getUserId(), retrievedPayment.get().getUserId(), "User ID seharusnya cocok"); // Verifikasi UUID
    }

    @Test
    void testFindById_NotFound() {
        // Cari pembayaran dengan ID yang tidak ada
        UUID randomId = UUID.randomUUID();
        Optional<Payment> retrievedPayment = paymentRepository.findById(randomId);

        assertFalse(retrievedPayment.isPresent(), "Pembayaran seharusnya tidak ditemukan untuk ID acak");
    }

    @Test
    void testFindAllPayments() {
        // Untuk memastikan hanya ada satu payment dari setUp,
        // Anda mungkin ingin membersihkan repository sebelum setiap tes, atau menyesuaikan assertion.
        // Jika setUp() selalu dijalankan dan menyimpan satu payment, maka size() akan 1.
        List<Payment> payments = paymentRepository.findAll();
        assertNotNull(payments, "Daftar pembayaran tidak boleh null");
        assertFalse(payments.isEmpty(), "Daftar pembayaran tidak boleh kosong setelah setUp");
        
        // Jika Anda membersihkan repo di awal setiap tes atau hanya menjalankan setUp sekali untuk semua tes di kelas ini
        // maka assertEquals(1, payments.size()); valid.
        // Jika tidak, jumlahnya bisa lebih dari 1 jika tes lain juga menyimpan payment.
        // Untuk tes yang lebih terisolasi, biasanya data lama dibersihkan.
        // Mari asumsikan hanya ada 1 data dari setUp saat ini.
        assertEquals(1, payments.size(), "Seharusnya hanya ada satu pembayaran dari setUp");
        assertEquals(testPayment.getId(), payments.get(0).getId(), "ID pembayaran di daftar seharusnya cocok");
    }
}