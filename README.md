# desafio
# Java
Com base na minaha analise segue as alteracoes:</br>
1- Mudei para usar try-with-resources para garantir que o BufferedReader seja fechado corretamente.</br>
2- Usei CopyOnWriteArrayList para evitar problemas de concorrência ao adicionar linhas. Isso é mais eficiente do que sincronizar manualmente uma lista comum.</br>
3- for disparava 10 threads, mas cada uma lia o arquivo inteiro do zero (data.txt) resultando na leitura do arquivo  10 vezes criando redundancia, Acaba utilizando memoria e CPU desnecessario.</br>
4- Adicionei tambem um if para evitar que a aplicação fique presa infinitamente se alguma task travar.Isso é mais eficiente do que sincronizar manualmente uma lista comum.</br>
5- Como boa pratica no catch o  e.printStackTrace(); não é adequado para produção. Entao adicionei uma linha de log.error para em uma possivel melhoria tratar a exception conforme for necessario.</br>

# C#
1- Substituição de List<string> por ConcurrentBag<string> → coleção thread-safe.</br>
2- Uso de Task.WhenAll para aguardar todas as tasks antes de prosseguir.</br>
3- Alteração do HttpClient para instância estática reutilizável.</br>
4- Adição de tratamento de exceções em DownloadAsync.</br>
5- Ajuste das mensagens de log → só imprimir "All downloads completed" após conclusão real dos downloads.
