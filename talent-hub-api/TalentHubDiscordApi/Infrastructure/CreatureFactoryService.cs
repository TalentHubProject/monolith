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

/// <inheritdoc />
public class CreatureFactoryService(
    IWebHostEnvironment env,
    ILogger<CreatureFactoryService> logger)
    : ICreatureFactoryService
{
    /// <summary>
    ///     Dictionnary of the whole creatures raceId to name.
    /// </summary>
    protected static readonly Dictionary<int, string> RaceIdToName = new()
    {
        { 1, "Cat" },
        { 2, "Cow" },
        { 3, "Mouse" },
        { 4, "Snake" }
    };

    /// <inheritdoc />
    public async Task<FileContentResult> CreateCreatureAsync(int level, int raceId)
    {
        var imagePath = GenerateImagePath(level, raceId);
        if (File.Exists(imagePath))
        {
            var imageBytes = await File.ReadAllBytesAsync(imagePath);
            return new FileContentResult(imageBytes, "image/png");
        }

        logger.LogWarning($"Image not found for level {level} and raceId {raceId}.");
        throw new FileNotFoundException("Image not found.");
    }

    /// <inheritdoc />
    public int GetRandomRaceId()
    {
        return new Random().Next(1, RaceIdToName.Count + 1);
    }

    /// <summary>
    ///     Generates the image path for the given level and raceId.
    /// </summary>
    /// <param name="level">The level of the creature.</param>
    /// <param name="raceId">The raceId of the creature.</param>
    /// <returns>The image path.</returns>
    private string GenerateImagePath(int level, int raceId)
    {
        var imageIndex = level / 5;
        const int maxEggIndex = 3;
        const int minCreatureIndex = 1;
        const int maxCreatureIndex = 3;

        var type = imageIndex <= maxEggIndex ? "Egg" : "Creature";

        if (type == "Creature")
        {
            imageIndex = Math.Max(minCreatureIndex, imageIndex - maxEggIndex);
            imageIndex = Math.Min(imageIndex, maxCreatureIndex);
        }

        var raceName = RaceIdToName[raceId];
        var folderName = type == "Egg" ? "Eggs" : "Creatures";
        var imageName = $"{type.ToLower()}_{imageIndex}.png";

        return Path.Combine("Assets", folderName, raceName, imageName);
    }
}