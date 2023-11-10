package de.neuefische.backend.security;

import lombok.Builder;

@Builder
public record AppUser(
        int id,
        String login,
        String avatarUrl
) {
}
