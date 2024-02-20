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

namespace MyFabulousCreatures.Infrastructure;

/// <summary>
///     Service for creating creatures.
/// </summary>
public interface ICreatureFactoryService
{
    /// <summary>
    ///     Creates a creature with the given level and raceId.
    /// </summary>
    /// <param name="level">The level of the creature.</param>
    /// <param name="raceId">The raceId of the creature.</param>
    /// <returns>A creature/egg image.</returns>
    Task<FileContentResult> CreateCreatureAsync(int level, int raceId);

    /// <summary>
    ///     Gets a random raceId.
    /// </summary>
    /// <returns>A random raceId.</returns>
    int GetRandomRaceId();
}