import type { Config } from 'jest';

const config: Config = {
  preset: 'jest-preset-angular',
  setupFilesAfterEnv: ['<rootDir>/setup-jest.ts'],
  moduleFileExtensions: ['ts', 'html', 'js', 'json', 'mjs'],
  snapshotSerializers: [
    'jest-preset-angular/build/serializers/no-ng-attributes',
    'jest-preset-angular/build/serializers/ng-snapshot',
    'jest-preset-angular/build/serializers/html-comment',
  ],
  testEnvironment: 'jsdom',
  transformIgnorePatterns: ['node_modules/(?!.*\\.mjs$)'],
  transform: {
    '^.+\\.(ts|js|mjs|html|svg)$': [
      'jest-preset-angular',
      {
        tsconfig: '<rootDir>/tsconfig.spec.json',
        stringifyContentPathRegex: '\\.(html|svg)$',
      },
    ],
  },
  collectCoverage: true,
  coverageDirectory: '<rootDir>/coverage',
  coverageReporters: ['lcov', 'cobertura', 'text-summary'],
  collectCoverageFrom: [
    'src/app/**/*.ts', // Specify where to collect coverage from
    '!src/main.ts', // ignore main file
    '!src/polyfills.ts', // ignore polyfill file
    '!src/test.ts', // ignore the test file
    '!src/**/*.module.ts', // ignore all modules
    '!src/**/*.stories.ts', // ignore all story files
    '!src/**/index.ts', // ignore index file
    '!src/app/models/**', //ignore models
    '!src/environments/**', // ignore environment files
  ],
  verbose: true,
  cacheDirectory: '<rootDir>/node_modules/.cache/jest',
};

export default config;
