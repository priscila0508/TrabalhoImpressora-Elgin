
public class Impressora {

    private boolean conectada = false;

    // 1. Abre a conexão com a impressora

    public boolean abrirConexao(int tipo, String modelo, String conexao, int parametro) {
        try {
            int resultado = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(
                    tipo, modelo, conexao, parametro);

            if (resultado == 0) {
                conectada = true;
                System.out.println("Conexão aberta com sucesso!");
            } else {
                conectada = false;
                System.out.println("Erro ao conectar com a impressora.");
            }

        } catch (Exception e) {
            conectada = false;
            System.out.println("Erro ao abrir conexão: " + e.getMessage());

        } catch (LinkageError e) {
            conectada = false;
            System.out.println("Erro ao carregar a DLL da impressora: " + e.getMessage());
        }

        return conectada;
    }

    // 2. Fecha a conexão com a impressora
    public void fecharConexao() {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();

                if (resultado == 0) {
                    conectada = false;
                    System.out.println("Conexão fechada com sucesso!");
                } else {
                    System.out.println("Erro ao fechar conexão.");
                }

            } catch (Exception e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());

            } catch (LinkageError e) {
                System.out.println("Erro de ligação com a DLL: " + e.getMessage());
            }
        } else {
            System.out.println("A impressora não está conectada.");
        }
    }

    // 3. Impressão de texto
    public void imprimirTexto(String dados, int posicao, int estilo, int tamanho) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ImpressaoTexto(
                        dados, posicao, estilo, tamanho);

                if (resultado < 0) {
                    System.out.println("Erro ao imprimir texto.");
                } else {
                    System.out.println("Texto enviado para impressão!");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 4. Corte do papel
    public void cortarPapel(int avanco) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.Corte(avanco);

                if (resultado == 0) {
                    System.out.println("Papel cortado com sucesso!");
                } else {
                    System.out.println("Erro ao cortar papel.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 5. Impressão de QR Code
    public void imprimirQRCode(String dados, int tamanho, int nivelCorrecao) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ImpressaoQRCode(
                        dados, tamanho, nivelCorrecao);

                if (resultado == 0) {
                    System.out.println("QR Code enviado para impressão!");
                } else {
                    System.out.println("Erro ao imprimir QR Code.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 6. Impressão de código de barras
    public void imprimirCodigoBarras(int tipo, String dados, int altura, int largura, int HRI) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(
                        tipo, dados, altura, largura, HRI);

                if (resultado == 0) {
                    System.out.println("Código de barras enviado para impressão!");
                } else {
                    System.out.println("Erro ao imprimir código de barras.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 7. Avanço de papel
    public void avancarPapel(int linhas) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);

                if (resultado == 0) {
                    System.out.println("Papel avançado com sucesso!");
                } else {
                    System.out.println("Erro ao avançar papel.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 8. Consulta o status da impressora
    public void consultarStatus(int parametro) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.StatusImpressora(parametro);

                if (resultado < 0) {
                    System.out.println("Erro ao consultar o status da impressora.");
                    return;
                }

                if (parametro == 5) {
                    System.out.println("===== STATUS DA IMPRESSORA =====");

                    if ((resultado & 1) != 0) {
                        System.out.println("Gaveta: aberta");
                    } else {
                        System.out.println("Gaveta: fechada");
                    }

                    if ((resultado & 2) != 0) {
                        System.out.println("Tampa: aberta");
                    } else {
                        System.out.println("Tampa: fechada");
                    }

                    if ((resultado & 8) != 0) {
                        System.out.println("Papel: ausente");
                    } else if ((resultado & 4) != 0) {
                        System.out.println("Papel: próximo do fim");
                    } else {
                        System.out.println("Papel: normal");
                    }
                    if ((resultado & 16) != 0) {
                        System.out.println("Ejetor: com papel");
                    } else {
                        System.out.println("Ejetor: sem papel");
                    }

                } else {
                    System.out.println("Status retornado: " + resultado);
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 9. Abre a gaveta Elgin
    public void abrirGavetaElgin(int pino, int tempoInicio, int tempoFim) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.AbreGavetaElgin(
                        pino, tempoInicio, tempoFim);

                if (resultado == 0) {
                    System.out.println("Gaveta aberta com sucesso!");
                } else {
                    System.out.println("Erro ao abrir gaveta.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 10. Abre a gaveta
    public void abrirGaveta(int pino, int tempoInicio, int tempoFim) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.AbreGaveta(
                        pino, tempoInicio, tempoFim);

                if (resultado == 0) {
                    System.out.println("Gaveta aberta com sucesso!");
                } else {
                    System.out.println("Erro ao abrir gaveta.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 11. Sinal sonoro
    public void sinalSonoro(int quantidade, int tempoInicio, int tempoFim) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.SinalSonoro(
                        quantidade, tempoInicio, tempoFim);

                if (resultado == 0) {
                    System.out.println("Sinal sonoro executado com sucesso!");
                } else {
                    System.out.println("Erro ao executar sinal sonoro.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 12. Ativa o modo página
    public void modoPagina() {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ModoPagina();

                if (resultado == 0) {
                    System.out.println("Modo página ativado!");
                } else {
                    System.out.println("Erro ao ativar modo página.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 13. Limpa o buffer do modo página
    public void limparBufferModoPagina() {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.LimpaBufferModoPagina();

                if (resultado == 0) {
                    System.out.println("Buffer limpo com sucesso!");
                } else {
                    System.out.println("Erro ao limpar buffer.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 14. Imprime o conteúdo do modo página
    public void imprimirModoPagina() {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ImprimeModoPagina();

                if (resultado == 0) {
                    System.out.println("Conteúdo impresso com sucesso!");
                } else {
                    System.out.println("Erro ao imprimir conteúdo.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 15. Retorna ao modo padrão
    public void modoPadrao() {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ModoPadrao();

                if (resultado == 0) {
                    System.out.println("Modo padrão ativado!");
                } else {
                    System.out.println("Erro ao ativar modo padrão.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 16. Define a posição horizontal
    public void posicaoHorizontal(int posicao) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.PosicaoImpressaoHorizontal(posicao);

                if (resultado == 0) {
                    System.out.println("Posição horizontal definida!");
                } else {
                    System.out.println("Erro ao definir posição horizontal.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 17. Define a posição vertical
    public void posicaoVertical(int posicao) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.PosicaoImpressaoVertical(posicao);

                if (resultado == 0) {
                    System.out.println("Posição vertical definida!");
                } else {
                    System.out.println("Erro ao definir posição vertical.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 18. Imprime XML SAT
    public void imprimirXMLSAT(String dados, int parametro) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(dados, parametro);

                if (resultado == 0) {
                    System.out.println("XML SAT enviado para impressão!");
                } else {
                    System.out.println("Erro ao imprimir XML SAT.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // 19. Imprime XML de cancelamento SAT
    public void imprimirXMLCancelamentoSAT(String dados, String assQRCode, int parametro) {
        if (conectada) {
            try {
                int resultado = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(
                        dados, assQRCode, parametro);

                if (resultado == 0) {
                    System.out.println("XML de cancelamento enviado para impressão!");
                } else {
                    System.out.println("Erro ao imprimir XML de cancelamento.");
                }

            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } else {
            System.out.println("Conecte a impressora primeiro!");
        }
    }

    // Verifica se a impressora está conectada
    public boolean estaConectada() {
        return conectada;
    }
}