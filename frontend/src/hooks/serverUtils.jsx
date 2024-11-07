import bcrypt from 'bcryptjs';

const server = 'http://127.0.0.1';
const port = 8080;

/**
  * Fetches data from a REST API server endpoint.
  *
  * @param {string} endpoint - REST API endpoint. Example: `/users/login`.
 * @param {Object} [options={}] - Optional fetch options - method, headers, and body.
  * @returns {Promise<Object>} The server response.
  * @throws {Error} If the request fails.
  */

export async function serverFetch(endpoint, options = {}) {
  try {
    const url = `${server}:${port}${endpoint}`;
    return await fetch(url, options);
  } catch (error) {
    console.error('Fetch error: ', error);
    throw error;
  }
}

export async function getHash(input) {
  const saltRounds = 10;
  const salt = await bcrypt.genSalt(saltRounds);
  const hash = await bcrypt.hash(input, salt);
  return hash;
}
