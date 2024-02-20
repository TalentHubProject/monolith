using Microsoft.AspNetCore.Mvc;
using MyFabulousCreatures.Infrastructure;

namespace MyFabulousCreatures.Controllers;

[ApiController]
[Route("/api/v1/creatures")]
public class LevelCreatureFactoryController(
    ICreatureFactoryService creatureFactoryService,
    ILogger<LevelCreatureFactoryController> logger)
    : ControllerBase
{
    [HttpGet("{level:int}/{raceId:int}")]
    public async Task<IActionResult> Get(int level, int raceId)
    {
        try
        {
            var result = await creatureFactoryService.CreateCreatureAsync(level, raceId);
            return File(result.FileContents, result.ContentType);
        }
        catch (FileNotFoundException ex)
        {
            logger.LogError(ex, "Failed to find creature image for level {Level} and raceId {RaceId}.", level, raceId);
            return NotFound();
        }
        catch (Exception ex)
        {
            logger.LogError(ex, "An error occurred while processing the request.");
            return StatusCode(500, "An error occurred while processing your request.");
        }
    }
}