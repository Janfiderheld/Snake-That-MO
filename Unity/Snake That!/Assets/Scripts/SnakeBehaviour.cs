using UnityEngine;

namespace com.MussundToeberg.SnakeThat
{
	public class SnakeBehaviour : MonoBehaviour
	{
        private const float FAST_VEL = 5.0f;
        private const float SLOW_VEL = 2.0f;

        public Vector2 StartingDirection = new Vector2(1, 0);
        public float velocity = FAST_VEL; 

		// Update is called once per frame
		void Update()
		{
            velocity = TouchController.Instance.IsCurrentlyTouched() ? SLOW_VEL : FAST_VEL;

            Vector3 larpedDirection = StartingDirection * velocity * Time.deltaTime;
            Vector3 newPos = transform.position + new Vector3(larpedDirection.x, larpedDirection.y);

            float rotationZ = Mathf.Acos(Vector3.Dot(transform.up, newPos - transform.position) / (transform.up.magnitude * (newPos - transform.position).magnitude));
            // TODO: Combine the following two lines if possible
            transform.Rotate(new Vector3(0, 0, 1), rotationZ);
            transform.SetPositionAndRotation(newPos, transform.rotation);
		}

        private void OnCollisionEnter2D(Collision2D collision)
        {
            StartingDirection = Vector2.Reflect(StartingDirection, collision.contacts[0].normal);
        }
    }	
}