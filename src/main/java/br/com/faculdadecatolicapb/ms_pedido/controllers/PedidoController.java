package br.com.faculdadecatolicapb.ms_pedido.controllers;

import br.com.faculdadecatolicapb.ms_pedido.dto.PedidoRequest;
import br.com.faculdadecatolicapb.ms_pedido.dto.PedidoResponse;
import br.com.faculdadecatolicapb.ms_pedido.services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> buscarTodos() {
        return ResponseEntity.ok(pedidoService.buscarTodos());
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> cadastrar(@RequestBody PedidoRequest pedidoRequest) {
        return ResponseEntity.ok(pedidoService.cadastrar(pedidoRequest));
    }

    @PatchMapping("/{id}/editar")
    public ResponseEntity<PedidoResponse> editar(@PathVariable Long id, PedidoRequest pedidoRequest) {
        return ResponseEntity.ok(pedidoService.editar(id, pedidoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        pedidoService.apagar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
