package tech.nan.demo.repository;

import tech.nan.demo.domain.terminal.Terminal;

public interface TerminalRepository {

    Terminal getTerminalById(Long id);
}
