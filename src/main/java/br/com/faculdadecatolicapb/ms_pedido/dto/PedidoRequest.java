package br.com.faculdadecatolicapb.ms_pedido.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PedidoRequest(
        @NotNull(message = "O ID do produto Ã© obrigatÃ³rio")
        Long produtoId,
        @Positive(message = "A quantidade de produtos deve ser maior que 0")
        Integer quantidade,
        BigDecimal valorTotal,
        @Email(message = "O email precisa ser válido!")
        String email
) {
}
