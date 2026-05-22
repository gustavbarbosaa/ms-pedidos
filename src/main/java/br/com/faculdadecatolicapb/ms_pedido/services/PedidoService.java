package br.com.faculdadecatolicapb.ms_pedido.services;

import br.com.faculdadecatolicapb.ms_pedido.config.RabbitMQConfig;
import br.com.faculdadecatolicapb.ms_pedido.controllers.ProdutoClient;
import br.com.faculdadecatolicapb.ms_pedido.domain.Pedido;
import br.com.faculdadecatolicapb.ms_pedido.dto.PedidoRequest;
import br.com.faculdadecatolicapb.ms_pedido.dto.PedidoResponse;
import br.com.faculdadecatolicapb.ms_pedido.dto.ProdutoResponse;
import br.com.faculdadecatolicapb.ms_pedido.exceptions.RecursoNaoEncontradoException;
import br.com.faculdadecatolicapb.ms_pedido.mapper.PedidoMapper;
import br.com.faculdadecatolicapb.ms_pedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado!";

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ProdutoClient produtoClient;
    private final RabbitTemplate rabbitTemplate;

    public PedidoResponse buscarPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(PEDIDO_NAO_ENCONTRADO));

        return pedidoMapper.toDTO(pedido);
    }

    public List<PedidoResponse> buscarTodos() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidos.stream().map(pedidoMapper::toDTO).toList();
    }

    public PedidoResponse cadastrar(PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoMapper.toEntity(pedidoRequest);

        ProdutoResponse produto = produtoClient.buscarPorId(pedido.getProdutoId());
        pedido.setValorTotal(produto.valor().multiply(BigDecimal.valueOf(pedidoRequest.quantidade())));

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, pedidoRequest.email());
        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toDTO(pedido);
    }

    public void apagar(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(PEDIDO_NAO_ENCONTRADO));

        pedidoRepository.delete(pedido);
    }

    public PedidoResponse editar(Long id, PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(PEDIDO_NAO_ENCONTRADO));

        ProdutoResponse produto = produtoClient.buscarPorId(pedido.getProdutoId());

        pedido.setQuantidade(pedidoRequest.quantidade());
        pedido.setValorTotal(produto.valor().multiply(BigDecimal.valueOf(pedidoRequest.quantidade())));
        pedido.setEmail(pedidoRequest.email());

        pedido = pedidoRepository.save(pedido);

        return pedidoMapper.toDTO(pedido);
    }
}
