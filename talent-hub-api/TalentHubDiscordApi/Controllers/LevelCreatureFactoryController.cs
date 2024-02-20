// Copyright (c) 2023 Talent Hub
// 
//  Licensed under the GPL-3.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at the root directory of this repository.
// 
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

using Microsoft.AspNetCore.Mvc;
using MyFabulousCreatures.Infrastructure;

namespace MyFabulousCreatures.Controllers;

/// <summary>
///     Controller for creating creatures.
/// </summary>
/// <param name="creatureFactoryService">The creature factory service.</param>
/// <param name="logger">The logger.</param>
[ApiController]
[Route("/api/v1/creatures")]
public class LevelCreatureFactoryController(
    ICreatureFactoryService creatureFactoryService,
    ILogger<LevelCreatureFactoryController> logger)
    : ControllerBase
{
    /// <summary>
    ///     Creates a creature with the given level and raceId.
    /// </summary>
    /// <param name="level">The level of the creature.</param>
    /// <param name="raceId">The raceId of the creature.</param>
    /// <returns>A creature/egg image.</returns>
    [HttpGet("{level:int}/{raceId:int}")]
    [ProducesResponseType(200)]
    [ProducesResponseType(404)]
    [ProducesResponseType(500)]
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