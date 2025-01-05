import bcrypt from 'bcryptjs';

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
    const baseUrl = import.meta.env.VITE_API_BASE_URL;
    const url = `${baseUrl}${endpoint}`;
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

export async function checkHash(password, hash) {
  const match = await bcrypt.compare(password, hash);
  console.log('Match->', match);
  return match;
}

export async function getUserData(userEmail) {
  const endpoint = `/users/profile/${userEmail}`;
  const options = {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  };

  try {
    const response = await serverFetch(endpoint, options);
    if (response.ok) {
      const data = await response.json();
      return data;
    } else {
      console.log('Failed to fetch data', response.statusText);
      return null;
    }
  } catch (error) {
    console.log(error);
    return null;
  }
}
