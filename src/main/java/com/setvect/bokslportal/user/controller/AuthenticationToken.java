package com.setvect.bokslportal.user.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;


@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticationToken {
  private final String userId;
  private final String name;
  private final Collection authorities;
  private final String token;
}
