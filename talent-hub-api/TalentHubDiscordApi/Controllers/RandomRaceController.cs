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
///     Controller for getting random raceId.
/// </summary>
/// <param name="creatureFactoryService">The creature factory service.</param>
[ApiController]
[Route("api/v1/[controller]")]
public class RandomRaceController(ICreatureFactoryService creatureFactoryService)
    : ControllerBase
{
    /// <summary>
    ///     Gets a random raceId.
    /// </summary>
    /// <returns>An Ok result with the random raceId.</returns>
    [HttpGet]
    [ProducesResponseType(200)]
    public IActionResult Get()
    {
        var raceId = creatureFactoryService.GetRandomRaceId();
        return Ok(raceId);
    }
}