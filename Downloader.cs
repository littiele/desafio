using System;
using System.Collections.Concurrent;
using System.Net.Http;
using System.Threading.Tasks;

public class Downloader
{
    private static readonly ConcurrentBag<string> cache = new();
    private static readonly HttpClient client = new();

    public static async Task Main()
    {
        var tasks = new Task[10];

        for (int i = 0; i < 10; i++)
            tasks[i] = DownloadAsync($"https://example.com/data/{i}");

        await Task.WhenAll(tasks);

        Console.WriteLine($"All downloads completed. Cache size: {cache.Count}");
    }

    private static async Task DownloadAsync(string url)
    {
        try
        {
            cache.Add(await client.GetStringAsync(url));
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Error downloading {url}: {ex.Message}");
        }
    }
}

