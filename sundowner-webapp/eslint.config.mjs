import { defineConfig } from "eslint/config";
import globals from "globals";
import js from "@eslint/js";
import tseslint from "typescript-eslint";
import jestPlugin from "eslint-plugin-jest";
import angular from "angular-eslint";
import angularTemplateParser from "@angular-eslint/template-parser";

export default defineConfig([
  {
    files: ["**/*.ts"],
    extends: [
      tseslint.configs.recommended,
      ...tseslint.configs.stylistic,
      ...angular.configs.tsRecommended,
    ],
    processor: angular.processInlineTemplates,
    rules: {
      "@angular-eslint/directive-selector": [
        "error",
        {
          type: "attribute",
          prefix: "app",
          style: "camelCase",
        },
      ],
      "@angular-eslint/component-selector": [
        "error",
        {
          type: "element",
          prefix: "app",
          style: "kebab-case",
        },
      ],
    },
  },
  {
    files: ["**/*.spec.ts", "**/*.test.ts"],
    plugins: {
      jest: jestPlugin,
    },
    rules: {
      ...jestPlugin.configs.recommended.rules,
      // mocks are any
      "@typescript-eslint/no-explicit-any": "off",
    },
  },
  {
    files: ["**/*.html", "**/*.scss"],
    plugins: {
      "@angular-eslint": angular,
    },
    languageOptions: {
      parser: angularTemplateParser,
    },
    extends: [
      ...angular.configs.templateRecommended,
      ...angular.configs.templateAccessibility,
    ],
    rules: {},
  },

  {
    files: ["**/*.{js,mjs,cjs,ts}"],
    languageOptions: { globals: globals.browser },
  },
  {
    files: ["**/*.{js,mjs,cjs}"],
    plugins: { js },
    extends: ["js/recommended"],
  },
  {
    ignores: ["node_modules/**/*", "dist/**/*", "coverage/**/*", "gensrc/**/*"],
  },
]);
